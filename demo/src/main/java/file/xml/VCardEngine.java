package file.xml;

import net.sourceforge.cardme.engine.ParameterType;
import net.sourceforge.cardme.engine.VCardLine;
import net.sourceforge.cardme.io.CompatibilityMode;
import net.sourceforge.cardme.util.Base64Wrapper;
import net.sourceforge.cardme.util.ISOUtils;
import net.sourceforge.cardme.util.VCardUtils;
import net.sourceforge.cardme.vcard.VCard;
import net.sourceforge.cardme.vcard.VCardImpl;
import net.sourceforge.cardme.vcard.arch.EncodingType;
import net.sourceforge.cardme.vcard.arch.VCardTypeName;
import net.sourceforge.cardme.vcard.arch.VCardVersion;
import net.sourceforge.cardme.vcard.errors.ErrorSeverity;
import net.sourceforge.cardme.vcard.errors.VCardError;
import net.sourceforge.cardme.vcard.exceptions.VCardParseException;
import net.sourceforge.cardme.vcard.features.*;
import net.sourceforge.cardme.vcard.types.*;
import net.sourceforge.cardme.vcard.types.media.AudioMediaType;
import net.sourceforge.cardme.vcard.types.media.ImageMediaType;
import net.sourceforge.cardme.vcard.types.media.KeyTextType;
import net.sourceforge.cardme.vcard.types.params.*;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.QuotedPrintableCodec;

import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Class copied from @see net.sourceforge.cardme.engine.VCardEngine;
 */
public class VCardEngine {

    private static final Pattern VCF_BEGIN_PATTERN = Pattern.compile("\\p{Blank}*((BEGIN)|(begin))\\p{Blank}*:\\p{Blank}*((VCARD)|(vcard))\\p{Blank}*");
    private static final Pattern VCF_END_PATTERN = Pattern.compile("\\p{Blank}*((END)|(end))\\p{Blank}*:\\p{Blank}*((VCARD)|(vcard))\\p{Blank}*");
    private static final Pattern VCF_GEO_PATTERN = Pattern.compile("-?\\d{1,3}\\.\\d{1,6};-?\\d{1,3}\\.\\d{1,6}");
    private static final QuotedPrintableCodec QP_CODEC = new QuotedPrintableCodec();
    private static final Pattern emptyLinePattern = Pattern.compile("$");
    private static final Pattern startQpLinePattern = Pattern.compile(".*;([ \\t]*ENCODING[ \\t]*=)?[ \\t]*QUOTED-PRINTABLE.*:.*=", Pattern.CASE_INSENSITIVE);
    private static final Pattern middleQpLinePattern = Pattern.compile("\\s*.+=?");
    private static final Pattern vcardTypePattern = Pattern.compile("^[ \\t]*\\p{ASCII}+:.*$");
    /**
     * <p>Selects from a list of application compatibility modes
     * to use when formatting the output of the vcard. Some applications
     * expect a certain type of formatting or non-standard types.</p>
     */
    private CompatibilityMode compatMode;
    /**
     * <p>If set, this charset will override any charset definition
     * when decoding strings from any type that defines "CHARSET".</p>
     */
    private Charset forceCharset = null;

    /**
     * <p>Create a VCard parsing engine and initialize it to
     * use RFC2426 compatibility mode by default.</p>
     */
    public VCardEngine() {
        compatMode = CompatibilityMode.RFC2426;
    }

    /**
     * <p>Create a VCard parsing engine with a user
     * specified compatibility mode.</p>
     */
    public VCardEngine(CompatibilityMode compatMode) {
        this.compatMode = compatMode;
    }

    /**
     * <p>Returns the currently set compatibility mode.
     * Null if not set.</p>
     *
     * @return {@link CompatibilityMode}
     */
    public CompatibilityMode getCompatibilityMode() {
        return compatMode;
    }

    /**
     * <p>Sets a specified compatibility mode.</p>
     *
     * @see CompatibilityMode
     */
    public void setCompatibilityMode(CompatibilityMode compatMode) {
        this.compatMode = Objects.requireNonNullElse(compatMode, CompatibilityMode.RFC2426);
    }

    /**
     * <p>Returns the charset that has been set
     * to be forced on all types, null if not set.</p>
     *
     * @return {@link Charset}
     */
    public Charset getForcedCharset() {
        return forceCharset;
    }

    /**
     * <p>Sets the charset to always be used when the
     * "CHARSET" parameter is encountered.</p>
     */
    public void setForcedCharset(String charset) {
        forceCharset = Charset.forName(charset);
    }

    /**
     * <p>Sets the charset to always be used when the
     * "CHARSET" parameter is encountered.</p>
     */
    public void setForcedCharset(Charset charset) {
        forceCharset = charset;
    }

    /**
     * <p>Returns true if the engine is enforcing a charset
     * on all types.</p>
     *
     * @return boolean
     */
    public boolean isCharsetForced() {
        return forceCharset != null;
    }

    /**
     * <p>Convenient method to parse an array of VCard files in one go.
     * This returns an array of VCard objects in the same order they were
     * parsed.</p>
     *
     * @return {@link VCard}[]
     */
    public VCard[] parse(File[] vcardFiles) throws IOException, VCardParseException {
        VCard[] vcards = new VCard[vcardFiles.length];
        for (int i = 0; i < vcardFiles.length; i++) {
            vcards[i] = parse(vcardFiles[i]);
        }

        return vcards;
    }

    /**
     * <p>Parses the specified VCard file by retrieving the contents
     * of the file, unfolding it and then parsing it. The returned result
     * is a VCard java object.</p>
     *
     * @return {@link VCard}
     */
    public VCard parse(File vcardFile) throws IOException, VCardParseException {
        String vcardStr = getContentFromFile(vcardFile);
        String unfoldedVcardStr = VCardUtils.unfoldVCard(vcardStr, compatMode);
        return parseVCard(unfoldedVcardStr);
    }

    /**
     * <p>Convenient method to parse an array of VCard strings in one go.
     * This returns an array of VCard objects in the same order they were
     * parsed.</p>
     *
     * @return {@link VCard}[]
     */
    public VCard[] parse(String[] vcardStrings) throws IOException, VCardParseException {
        VCard[] vcards = new VCard[vcardStrings.length];
        for (int i = 0; i < vcards.length; i++) {
            vcards[i] = parse(vcardStrings[i]);
        }

        return vcards;
    }

    /**
     * <p>Parses the specified VCard String by retrieving the contents
     * of the file, unfolding it and then parsing it. The returned result
     * is a VCard java object.</p>
     *
     * @return {@link VCard}
     */
    public VCard parse(String vcardString) throws IOException, VCardParseException {
        String vcardStr = getContentFromString(vcardString);
        String unfoldedVcardStr = VCardUtils.unfoldVCard(vcardStr);
        return parseVCard(unfoldedVcardStr);
    }

    /**
     * <p>Returns an iterator of VCards given a path that can
     * contain an arbitrary number of vcards inside it.</p>
     *
     * @return {@link List}&lt;VCard&gt;
     */
    public List<VCard> parseMultiple(String vcardPath) throws IOException, VCardParseException {
        return parseMultiple(new File(vcardPath));
    }

    /**
     * <p>Returns an iterator of VCards given a file that can
     * contain an arbitrary number of vcards inside it.</p>
     *
     * @return {@link List}&lt;VCard&gt;
     */
    public List<VCard> parseMultiple(File vcardFile) throws IOException, VCardParseException {
        String vcardStr = getContentFromFile(vcardFile);
        String unfoldedVcardStr = VCardUtils.unfoldVCard(vcardStr);
        return parseManyInOneVCard(unfoldedVcardStr);
    }

    /**
     * <p>Parses the specified VCard String and returns a VCard java object.
     * Should errors occur during the parsing of the VCard they will be handled
     * within the VCard error handling interface. These errors can be retrieved
     * via the <code>{@link VCardImpl}.getErrors()</code> method.<br/>
     * <p>
     * This method assumes the following:
     * <ol>
     * <li>The String has only \n end of line markers instead of \r\n</li>
     * <li>All lines in the String are unfolded</li>
     * </ol>
     * </p>
     *
     * @return {@link VCard}
     */
    private VCard parseVCard(String vcardStr) throws VCardParseException {
        VCardImpl vcard = new VCardImpl();
        vcard.setThrowExceptions(false);

        String[] lines = vcardStr.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String vLine = lines[i];
            VCardLine parsedLine = VCardLine.parse(vLine);

            if (parsedLine != null) {
                try {
                    parseLine(parsedLine, vcard);
                } catch (VCardParseException vpe) {
                    if (vcard.isThrowExceptions()) {
                        throw vpe;
                    } else {
                        handleError(vcard, vpe.getMessage(), vpe, ErrorSeverity.WARNING);
                    }
                }
            } else {
                if (vcard.isThrowExceptions()) {
                    throw new VCardParseException("Invalid data in VCard on line " + i);
                } else {
                    handleError(vcard, "Invalid data in VCard on line " + i, null, ErrorSeverity.FATAL);
                }
            }
        }

        return vcard;
    }

    private List<VCard> parseManyInOneVCard(String vcardStr) throws VCardParseException {
        List<VCard> vcards = new ArrayList<>();
        List<String> enumCards = enumerateVCards(vcardStr);

        for (String card : enumCards) {
            VCard vcard = parseVCard(card);
            vcards.add(vcard);
        }

        return vcards;
    }

    private List<String> enumerateVCards(String vcardString) {
        List<String> vcardStrings = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String[] split = vcardString.split("\r?\n");
        boolean begin = false;
        boolean end = false;

        for (String s : split) {
            if (VCF_BEGIN_PATTERN.matcher(s).matches()) {
                begin = true;
            }

            if (VCF_END_PATTERN.matcher(s).matches()) {
                end = true;
            }

            if (begin && !end) {
                sb.append(s);
                sb.append('\n');
            }

            if (end) {
                sb.append(s);
                sb.append('\n');
                begin = false;
                end = false;
                vcardStrings.add(sb.toString());
                sb = new StringBuilder();
            }
        }

        return vcardStrings;
    }

    /**
     * <p>Parses a specific line of text from the VCard. This line is partitioned into
     * the following segments.
     * <ul>
     * <li><b>group</b> Optional grouping name for the type.</li>
     * <li><b>type</b> The vcard type that identifies this line.</li>
     * <li><b>paramTypes</b> Any parameters that follow the type.</li>
     * <li><b>value</b> The actual value or data of the type.</li>
     * <li><b>vcard</b> The vcard object to append the type to once parsed.</li>
     * </ul>
     * </p>
     */
    private void parseLine(VCardLine parsedLine, VCardImpl vcard) throws VCardParseException {

        String group = parsedLine.getGroup();
        String value = parsedLine.getValue().trim();
        String type = parsedLine.getTypeName().trim().toUpperCase();
        List<ParameterType> paramTypes = parseParamTypesLine(parsedLine.getParameters());

        VCardTypeName vCardTypeName;

        //Extended Types are a bit special since they only start with X- and end with anything.
        if (type.startsWith("X-")) {
            vCardTypeName = VCardTypeName.XTENDED;
        } else {
            try {
                //Enums do not like hyphens so replace it with an underscore.
                type = type.replaceAll("-", "_");
                vCardTypeName = VCardTypeName.valueOf(type);
            } catch (IllegalArgumentException iae) {
                if (vcard.isThrowExceptions()) {
                    throw new VCardParseException(iae.getMessage(), iae);
                } else {
                    handleError(vcard, "Unrecognizable type name \"" + type + "\"", iae, ErrorSeverity.WARNING);
                    return;
                }
            }
        }

        switch (vCardTypeName) {
            case BEGIN: {
                parseBeginType(group, value, vcard);
                break;
            }

            case END: {
                parseEndType(group, value, vcard);
                break;
            }

            case VERSION: {
                parseVersionType(group, value, vcard);
                break;
            }

            case FN: {
                parseFnType(group, value, paramTypes, vcard);
                break;
            }

            case N: {
                parseNType(group, value, paramTypes, vcard);
                break;
            }

            case NICKNAME: {
                parseNicknameType(group, value, paramTypes, vcard);
                break;
            }

            case PHOTO: {
                parsePhotoType(group, value, paramTypes, vcard);
                break;
            }

            case BDAY: {
                parseBDayType(group, value, paramTypes, vcard);
                break;
            }

            case ADR: {
                parseAdrType(group, value, paramTypes, vcard);
                break;
            }

            case LABEL: {
                parseLabelType(group, value, paramTypes, vcard);
                break;
            }

            case TEL: {
                parseTelType(group, value, paramTypes, vcard);
                break;
            }

            case EMAIL: {
                parseEmailType(group, value, paramTypes, vcard);
                break;
            }

            case MAILER: {
                parseMailerType(group, value, paramTypes, vcard);
                break;
            }

            case TZ: {
                parseTzType(group, value, paramTypes, vcard);
                break;
            }

            case GEO: {
                parseGeoType(group, value, paramTypes, vcard);
                break;
            }

            case TITLE: {
                parseTitleType(group, value, paramTypes, vcard);
                break;
            }

            case ROLE: {
                parseRoleType(group, value, paramTypes, vcard);
                break;
            }

            case LOGO: {
                parseLogoType(group, value, paramTypes, vcard);
                break;
            }

            case AGENT: {
                //TODO	parseAgentType(group, value, vcard);
                break;
            }

            case ORG: {
                parseOrgType(group, value, paramTypes, vcard);
                break;
            }

            case CATEGORIES: {
                parseCategoriesType(group, value, paramTypes, vcard);
                break;
            }

            case NOTE: {
                parseNoteType(group, value, paramTypes, vcard);
                break;
            }

            case PRODID: {
                parseProdIdType(group, value, paramTypes, vcard);
                break;
            }

            case REV: {
                parseRevType(group, value, paramTypes, vcard);
                break;
            }

            case SORT_STRING: {
                parseSortStringType(group, value, paramTypes, vcard);
                break;
            }

            case SOUND: {
                parseSoundType(group, value, paramTypes, vcard);
                break;
            }

            case UID: {
                parseUidType(group, value, paramTypes, vcard);
                break;
            }

            case URL: {
                parseUrlType(group, value, paramTypes, vcard);
                break;
            }

            case CLASS: {
                parseClassType(group, value, paramTypes, vcard);
                break;
            }

            case KEY: {
                parseKeyType(group, value, paramTypes, vcard);
                break;
            }

            case XTENDED: {
                parseXtendedType(group, value, type, paramTypes, vcard);
                break;
            }

            case NAME: {
                parseNameType(group, value, paramTypes, vcard);
                break;
            }

            case PROFILE: {
                parseProfileType(group, value, paramTypes, vcard);
                break;
            }

            case SOURCE: {
                parseSourceType(group, value, paramTypes, vcard);
                break;
            }

            case IMPP: {
                parseImppType(group, value, paramTypes, vcard);
                break;
            }

            default: {
                throw new VCardParseException("Unhandled VCard type \"" + vCardTypeName.getType() + "\"");
            }
        }
    }

    /**
     * <p>Parses the BEGIN type.</p>
     */
    private void parseBeginType(String group, String value, VCardImpl vcard) throws VCardParseException {
        try {
            BeginType beginType = new BeginType();
            if ("VCARD".compareToIgnoreCase(value) == 0) {
                if (group != null) {
                    beginType.setGroup(group);
                }

                vcard.setBegin(beginType);
            } else {
                throw new VCardParseException("Invalid value for \"BEGIN\" type. Must be \"VCARD\"");
            }
        } catch (Exception ex) {
            throw new VCardParseException("BeginType (" + VCardTypeName.BEGIN.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the END type.</p>
     */
    private void parseEndType(String group, String value, VCardImpl vcard) throws VCardParseException {
        try {
            EndType endType = new EndType();
            if ("VCARD".compareToIgnoreCase(value) == 0) {
                if (group != null) {
                    endType.setGroup(group);
                }

                vcard.setEnd(endType);
            } else {
                throw new VCardParseException("Invalid value for \"END\" type. Must be \"VCARD\"");
            }
        } catch (Exception ex) {
            throw new VCardParseException("EndType (" + VCardTypeName.END.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the VERSION type.</p>
     */
    private void parseVersionType(String group, String value, VCardImpl vcard) throws VCardParseException {
        try {
            VersionType versionType = new VersionType();
            if (VCardVersion.V3_0.getVersion().compareTo(value) == 0) {
                versionType.setVersion(VCardVersion.V3_0);
            } else if (VCardVersion.V2_1.getVersion().compareTo(value) == 0) {
                versionType.setVersion(VCardVersion.V2_1);
            } else if (VCardVersion.V4_0.getVersion().compareTo(value) == 0) {
                versionType.setVersion(VCardVersion.V4_0);
            } else {
                throw new VCardParseException("Invalid value for \"VERSION\" type. Must be 2.1 or 3.0 or 4.0]");
            }

            if (group != null) {
                versionType.setGroup(group);
            }

            vcard.setVersion(versionType);
        } catch (Exception ex) {
            throw new VCardParseException("VersionType (" + VCardTypeName.VERSION.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the FN type.</p>
     */
    private void parseFnType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            FNType fnType = new FNType();
            parseParamTypes(fnType, paramTypeList, value, VCardTypeName.FN);

            if (fnType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(fnType, value);
            }

            fnType.setFormattedName(VCardUtils.unescapeString(value));

            if (group != null) {
                fnType.setGroup(group);
            }

            vcard.setFN(fnType);
        } catch (Exception ex) {
            throw new VCardParseException("FNType (" + VCardTypeName.FN.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the N type.</p>
     */
    private void parseNType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            NType nType = new NType();
            parseParamTypes(nType, paramTypeList, value, VCardTypeName.N);

            if (nType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(nType, value);
            }

            if (compatMode == CompatibilityMode.MS_OUTLOOK) {
                parseNTypeOutlook(nType, value);
            } else {
                parseNTypeRFC(nType, value);
            }

            if (group != null) {
                nType.setGroup(group);
            }

            vcard.setN(nType);
        } catch (Exception ex) {
            throw new VCardParseException("NType (" + VCardTypeName.N.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    private void parseNTypeOutlook(NType nType, String value) {
        String[] names = VCardUtils.parseStringWithEscappedDelimiter(value, ';');

        for (int i = 0; i < names.length; i++) {
            switch (i) {
                case 0: {
                    nType.setFamilyName(VCardUtils.unescapeString(names[0]));

                    break;
                }

                case 1: {
                    nType.setGivenName(VCardUtils.unescapeString(names[1]));

                    break;
                }

                case 2: {
                    String[] addNames = VCardUtils.parseStringWithEscappedDelimiter(names[2], ',');
                    for (String addName : addNames) {
                        nType.addAdditionalName(VCardUtils.unescapeString(addName));
                    }

                    break;
                }

                case 3: {
                    String[] prefixes = VCardUtils.parseStringWithEscappedDelimiter(names[3], ',');
                    for (String prefix : prefixes) {
                        nType.addHonorificPrefix(VCardUtils.unescapeString(prefix));
                    }

                    break;
                }

                case 4: {
                    String[] suffixes = VCardUtils.parseStringWithEscappedDelimiter(names[4], ',');
                    for (String suffix : suffixes) {
                        nType.addHonorificSuffix(VCardUtils.unescapeString(suffix));
                    }

                    break;
                }
            }
        }
    }

    private void parseNTypeRFC(NType nType, String value) {
        String[] names = VCardUtils.parseStringWithEscappedDelimiter(value, ';');

        int cur = 0;
        if (names.length > cur && names[cur] != null) {
            if (VCardUtils.needsUnEscaping(names[cur])) {
                nType.setFamilyName(VCardUtils.unescapeString(names[cur]));
            } else {
                nType.setFamilyName(names[cur]);
            }
        }
        cur++;

        if (names.length > cur && names[cur] != null) {
            if (VCardUtils.needsUnEscaping(names[cur])) {
                nType.setGivenName(VCardUtils.unescapeString(names[cur]));
            } else {
                nType.setGivenName(names[cur]);
            }
        }
        cur++;

        if (names.length > cur && names[cur] != null) {
            String[] addNames = VCardUtils.parseStringWithEscappedDelimiter(names[cur], ',');
            for (String addName : addNames) {
                if (VCardUtils.needsUnEscaping(addName)) {
                    nType.addAdditionalName(VCardUtils.unescapeString(addName));
                } else {
                    nType.addAdditionalName(addName);
                }
            }
        }
        cur++;

        if (names.length > cur && names[cur] != null) {
            String[] prefixes = VCardUtils.parseStringWithEscappedDelimiter(names[cur], ',');
            for (String prefix : prefixes) {
                if (VCardUtils.needsUnEscaping(prefix)) {
                    nType.addHonorificPrefix(VCardUtils.unescapeString(prefix));
                } else {
                    nType.addHonorificPrefix(prefix);
                }
            }
        }
        cur++;

        if (names.length > cur && names[cur] != null) {
            String[] suffixes = VCardUtils.parseStringWithEscappedDelimiter(names[cur], ',');
            for (String suffix : suffixes) {
                if (VCardUtils.needsUnEscaping(suffix)) {
                    nType.addHonorificSuffix(VCardUtils.unescapeString(suffix));
                } else {
                    nType.addHonorificSuffix(suffix);
                }
            }
        }
    }

    /**
     * <p>Parses the Nickname type.</p>
     */
    private void parseNicknameType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            NicknameType nicknameType = new NicknameType();
            parseParamTypes(nicknameType, paramTypeList, value, VCardTypeName.NICKNAME);

            if (nicknameType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(nicknameType, value);
            }

            String[] nicknames = VCardUtils.parseStringWithEscappedDelimiter(value, ',');
            for (String nickname : nicknames) {
                if (VCardUtils.needsUnEscaping(nickname)) {
                    nicknameType.addNickname(VCardUtils.unescapeString(nickname));
                } else {
                    nicknameType.addNickname(nickname);
                }
            }

            if (group != null) {
                nicknameType.setGroup(group);
            }

            vcard.setNickname(nicknameType);
        } catch (Exception ex) {
            throw new VCardParseException("NicknameType (" + VCardTypeName.NICKNAME.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the PHOTO type.</p>
     */
    private void parsePhotoType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            PhotoType photoType = new PhotoType();
            parseParamTypes(photoType, paramTypeList, value, VCardTypeName.PHOTO);

            if (photoType.isBinary()) {
                byte[] photoBytes = Base64Wrapper.decode(value);
                photoType.setCompression(false);
                photoType.setPhoto(photoBytes);
            } else {
                URI photoUri = new URI(value);
                photoType.setPhotoURI(photoUri);
            }

            if (group != null) {
                photoType.setGroup(group);
            }

            vcard.addPhoto(photoType);
        } catch (Exception ex) {
            throw new VCardParseException("PhotoType (" + VCardTypeName.PHOTO.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the BDAY type.</p>
     */
    private void parseBDayType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            BDayType bdayType = new BDayType();
            parseParamTypes(bdayType, paramTypeList, value, VCardTypeName.BDAY);

            Calendar cal = ISOUtils.parseISO8601Date(value);
            bdayType.setBirthday(cal);

            if (group != null) {
                bdayType.setGroup(group);
            }

            vcard.setBDay(bdayType);
        } catch (Exception ex) {
            throw new VCardParseException("BDayType (" + VCardTypeName.BDAY.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the ADR type.</p>
     */
    private void parseAdrType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            AdrType adrType = new AdrType();
            parseParamTypes(adrType, paramTypeList, value, VCardTypeName.ADR);

            if (adrType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(adrType, value);
            }

            String[] address = VCardUtils.parseStringWithEscappedDelimiter(value, ';');
            int i = 0;
            String postOfficeBox = (address.length > i) ? address[i] : null;
            i++;
            String extendedAddress = (address.length > i) ? address[i] : null;
            i++;
            String streetAddress = (address.length > i) ? address[i] : null;
            i++;
            String locality = (address.length > i) ? address[i] : null;
            i++;
            String region = (address.length > i) ? address[i] : null;
            i++;
            String postalCode = (address.length > i) ? address[i] : null;
            i++;
            String countryName = (address.length > i) ? address[i] : null;

            if (postOfficeBox != null) {
                adrType.setPostOfficeBox(VCardUtils.unescapeString(postOfficeBox));
            }

            if (extendedAddress != null) {
                adrType.setExtendedAddress(VCardUtils.unescapeString(extendedAddress));
            }

            if (streetAddress != null) {
                adrType.setStreetAddress(VCardUtils.unescapeString(streetAddress));
            }

            if (locality != null) {
                adrType.setLocality(VCardUtils.unescapeString(locality));
            }

            if (region != null) {
                adrType.setRegion(VCardUtils.unescapeString(region));
            }

            if (postalCode != null) {
                adrType.setPostalCode(VCardUtils.unescapeString(postalCode));
            }

            if (countryName != null) {
                adrType.setCountryName(VCardUtils.unescapeString(countryName));
            }

            if (group != null) {
                adrType.setGroup(group);
            }

            vcard.addAdr(adrType);
        } catch (Exception ex) {
            throw new VCardParseException("AdrType (" + VCardTypeName.ADR.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the LABEL type.</p>
     */
    private void parseLabelType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            LabelType labelType = new LabelType();
            parseParamTypes(labelType, paramTypeList, value, VCardTypeName.LABEL);

            if (labelType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(labelType, value);
            }

            labelType.setLabel(VCardUtils.unescapeString(value));

            if (group != null) {
                labelType.setGroup(group);
            }

            boolean match = false;
            List<AdrType> adrList = vcard.getAdrs();

            for (AdrType adrType : adrList) {
                if (!match) {
                    //Get address and all its parameter and extended parameter types
                    List<AdrParamType> aPrmList = adrType.getParams();
                    List<ExtendedParamType> aXPrmList = adrType.getExtendedParams();

                    List<LabelParamType> lPrmList = labelType.getParams();
                    List<ExtendedParamType> lXPrmList = labelType.getExtendedParams();


                    //See how many address parameter types match each label parameter type
                    int paramsMatched = 0;
                    for (LabelParamType labelParamType : lPrmList) {
                        for (AdrParamType adrParamType : aPrmList) {
                            if (adrParamType.getType().equals(labelParamType.getType())) {
                                paramsMatched++;
                            }
                        }
                    }

                    //See how many extended address parameter types match each extended label parameter type
                    int xparamsMatched = 0;
                    for (ExtendedParamType labelXParamType : lXPrmList) {
                        for (ExtendedParamType adrXParamType : aXPrmList) {
                            if (adrXParamType.hasTypeValue()) {
                                if (adrXParamType.getTypeName().equals(labelXParamType.getTypeName()) &&
                                        adrXParamType.getTypeValue().equals(labelXParamType.getTypeValue())) {
                                    xparamsMatched++;
                                }
                            } else {
                                if (adrXParamType.getTypeName().equals(labelXParamType.getTypeName())) {
                                    xparamsMatched++;
                                }
                            }
                        }
                    }

                    //If the number of matching parameter types match between the label
                    //and the address then this label belongs to the respective address.
                    if (paramsMatched == labelType.getParamSize() && xparamsMatched == labelType.getExtendedParamSize()) {
                        //Only set the label on the address if it does not already have one

                        if (!adrType.hasLabel()) {
                            adrType.setLabel(labelType);
                        } else {
                            vcard.addError("Label with duplicate parameter types was detected and ignored. Label -> " + labelType, ErrorSeverity.WARNING, new VCardParseException("Duplicate label"));
                        }

                        match = true;
                    }
                }//if
            }//for
        } catch (Exception ex) {
            throw new VCardParseException("LabelType (" + VCardTypeName.LABEL.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the TEL type.</p>
     */
    private void parseTelType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            TelType telType = new TelType();
            parseParamTypes(telType, paramTypeList, value, VCardTypeName.TEL);

            if (telType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(telType, value);
            }

            if (group != null) {
                telType.setGroup(group);
            }

            telType.setTelephone(VCardUtils.unescapeString(value));


            vcard.addTel(telType);
        } catch (Exception ex) {
            throw new VCardParseException("TelType (" + VCardTypeName.TEL.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the EMAIL type.</p>
     */
    private void parseEmailType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            EmailType emailType = new EmailType();
            parseParamTypes(emailType, paramTypeList, value, VCardTypeName.EMAIL);

            if (EncodingType.BINARY.equals(emailType.getEncodingType())) {
                byte[] emailBytes = Base64Wrapper.decode(value);

                if (emailType.hasCharset()) {
                    emailType.setEmail(new String(emailBytes, emailType.getCharset()));
                } else {
                    emailType.setEmail(new String(emailBytes, Charset.defaultCharset()));
                }
            } else {
                if (emailType.isQuotedPrintable()) {
                    value = decodeQuotedPrintableValue(emailType, value);
                }

                emailType.setEmail(value);
            }

            if (group != null) {
                emailType.setGroup(group);
            }

            vcard.addEmail(emailType);
        } catch (Exception ex) {
            throw new VCardParseException("EmailType (" + VCardTypeName.EMAIL.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the MAILER type.</p>
     */
    private void parseMailerType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            MailerType mailerType = new MailerType();
            parseParamTypes(mailerType, paramTypeList, value, VCardTypeName.MAILER);

            if (mailerType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(mailerType, value);
            }

            if (group != null) {
                mailerType.setGroup(group);
            }

            mailerType.setMailer(VCardUtils.unescapeString(value));

            vcard.setMailer(mailerType);
        } catch (Exception ex) {
            throw new VCardParseException("MailerType (" + VCardTypeName.MAILER.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the TZ type.</p>
     */
    private void parseTzType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            TzType tzType = new TzType();
            parseParamTypes(tzType, paramTypeList, value, VCardTypeName.TZ);

            if (tzType.isText()) {
                //example) TZ;VALUE=text:-05:00; EST; Raleigh/North America

                String[] split = VCardUtils.parseStringWithEscappedDelimiter(value, ';');
                int cur = 0;

                if (split.length > cur && split[cur].length() > 0) {
                    tzType.parseTimeZoneOffset(VCardUtils.unescapeString(split[cur]));
                }

                cur++;
                if (split.length > cur && split[cur].length() > 0) {
                    tzType.setShortText(VCardUtils.unescapeString(split[cur]));
                }

                cur++;
                if (split.length > cur && split[cur].length() > 0) {
                    tzType.setLongText(VCardUtils.unescapeString(split[cur]));
                }
            } else {
                tzType.parseTimeZoneOffset(value);
            }

            if (group != null) {
                tzType.setGroup(group);
            }

            vcard.setTz(tzType);
        } catch (Exception ex) {
            throw new VCardParseException("TZType (" + VCardTypeName.TZ.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the GEO type.</p>
     */
    private void parseGeoType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            GeoType geoType = new GeoType();
            parseParamTypes(geoType, paramTypeList, value, VCardTypeName.GEO);

            if (VCF_GEO_PATTERN.matcher(value).matches()) {
                String[] geo = value.split(";");
                String lat = geo[0];
                String lon = geo[1];
                geoType.setLatitude(Float.parseFloat(lat));
                geoType.setLongitude(Float.parseFloat(lon));

                if (group != null) {
                    geoType.setGroup(group);
                }

                vcard.setGeo(geoType);
            } else {
                throw new VCardParseException("GeoType (" + VCardTypeName.GEO.getType() + ") GeoType is not valid.");
            }
        } catch (Exception ex) {
            throw new VCardParseException("GeoType (" + VCardTypeName.GEO.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the TITLE type.</p>
     */
    private void parseTitleType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            TitleType titleType = new TitleType();
            parseParamTypes(titleType, paramTypeList, value, VCardTypeName.TITLE);

            if (titleType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(titleType, value);
            }

            if (group != null) {
                titleType.setGroup(group);
            }

            titleType.setTitle(VCardUtils.unescapeString(value));
            vcard.setTitle(titleType);
        } catch (Exception ex) {
            throw new VCardParseException("TitleType (" + VCardTypeName.TITLE.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the ROLE type.</p>
     */
    private void parseRoleType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            RoleType roleType = new RoleType();
            parseParamTypes(roleType, paramTypeList, value, VCardTypeName.ROLE);

            if (roleType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(roleType, value);
            }

            if (group != null) {
                roleType.setGroup(group);
            }

            roleType.setRole(VCardUtils.unescapeString(value));
            vcard.setRole(roleType);
        } catch (Exception ex) {
            throw new VCardParseException("RoleType (" + VCardTypeName.ROLE.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the LOGO type.</p>
     */
    private void parseLogoType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            LogoType logoType = new LogoType();
            parseParamTypes(logoType, paramTypeList, value, VCardTypeName.LOGO);

            if (logoType.isBinary()) {
                byte[] logoBytes = Base64Wrapper.decode(value);
                logoType.setCompression(false);
                logoType.setLogo(logoBytes);
            } else {
                URI logoUri = new URI(value);
                logoType.setLogoURI(logoUri);
            }

            if (group != null) {
                logoType.setGroup(group);
            }

            vcard.addLogo(logoType);
        } catch (Exception ex) {
            throw new VCardParseException("LogoType (" + VCardTypeName.LOGO.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the ORG type.</p>
     */
    private void parseOrgType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            OrgType orgType = new OrgType();
            parseParamTypes(orgType, paramTypeList, value, VCardTypeName.ORG);

            if (orgType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(orgType, value);
            }

            String[] orgUnits = VCardUtils.parseStringWithEscappedDelimiter(value, ';');
            if (orgUnits.length > 0) {
                //First in list is the organization name
                orgType.setOrgName(orgUnits[0]);

                //The rest are organizational units
                for (int i = 1; i < orgUnits.length; i++) {
                    if (VCardUtils.needsUnEscaping(orgUnits[i])) {
                        String unesc = VCardUtils.unescapeString(orgUnits[i]);
                        orgType.addOrgUnit(unesc);
                    } else {
                        orgType.addOrgUnit(orgUnits[i]);
                    }
                }
            }

            if (group != null) {
                orgType.setGroup(group);
            }

            vcard.setOrg(orgType);
        } catch (Exception ex) {
            throw new VCardParseException("OrgType (" + VCardTypeName.ORG.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the CATEGORIES type.</p>
     */
    private void parseCategoriesType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            CategoriesType categoriesType = new CategoriesType();
            parseParamTypes(categoriesType, paramTypeList, value, VCardTypeName.CATEGORIES);

            if (categoriesType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(categoriesType, value);
            }

            String[] categories;
            if (compatMode == CompatibilityMode.KDE_ADDRESS_BOOK) {
                categories = VCardUtils.unescapeString(value).split(",");
            } else {
                categories = VCardUtils.parseStringWithEscappedDelimiter(value, ',');
            }

            for (String category : categories) {
                categoriesType.addCategory(VCardUtils.unescapeString(category));
            }

            if (group != null) {
                categoriesType.setGroup(group);
            }

            vcard.setCategories(categoriesType);
        } catch (Exception ex) {
            throw new VCardParseException("CategoriesType (" + VCardTypeName.CATEGORIES.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the NOTE type.</p>
     */
    private void parseNoteType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            NoteType noteType = new NoteType();
            parseParamTypes(noteType, paramTypeList, value, VCardTypeName.NOTE);

            if (noteType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(noteType, value);
            }

            if (group != null) {
                noteType.setGroup(group);
            }

            noteType.setNote(VCardUtils.unescapeString(value));
            vcard.addNote(noteType);
        } catch (Exception ex) {
            throw new VCardParseException("NoteType (" + VCardTypeName.NOTE.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the PRODID type.</p>
     */
    private void parseProdIdType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            ProdIdType prodIdType = new ProdIdType();
            parseParamTypes(prodIdType, paramTypeList, value, VCardTypeName.PRODID);

            if (prodIdType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(prodIdType, value);
            }

            if (group != null) {
                prodIdType.setGroup(group);
            }

            prodIdType.setProdId(VCardUtils.unescapeString(value));
            vcard.setProdId(prodIdType);
        } catch (Exception ex) {
            throw new VCardParseException("ProdIdType (" + VCardTypeName.PRODID.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the REV type.</p>
     */
    private void parseRevType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            RevType revType = new RevType();
            parseParamTypes(revType, paramTypeList, value, VCardTypeName.REV);

            Calendar cal = ISOUtils.parseISO8601Date(value);
            revType.setRevision(cal);

            if (group != null) {
                revType.setGroup(group);
            }

            vcard.setRev(revType);
        } catch (Exception ex) {
            throw new VCardParseException("RevType (" + VCardTypeName.REV.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the SORT-STRING type.</p>
     */
    private void parseSortStringType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            SortStringType sortStringType = new SortStringType();
            parseParamTypes(sortStringType, paramTypeList, value, VCardTypeName.SORT_STRING);

            if (sortStringType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(sortStringType, value);
            }

            if (group != null) {
                sortStringType.setGroup(group);
            }

            sortStringType.setSortString(VCardUtils.unescapeString(value));
            vcard.setSortString(sortStringType);
        } catch (Exception ex) {
            throw new VCardParseException("SortStringType (" + VCardTypeName.SORT_STRING.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the SOUND type.</p>
     */
    private void parseSoundType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            SoundType soundType = new SoundType();
            parseParamTypes(soundType, paramTypeList, value, VCardTypeName.SOUND);

            if (soundType.isBinary()) {
                byte[] soundBytes = Base64Wrapper.decode(value);
                soundType.setCompression(false);
                soundType.setSound(soundBytes);
            } else {
                URI soundUri = new URI(value);
                soundType.setSoundURI(soundUri);
            }

            if (group != null) {
                soundType.setGroup(group);
            }

            vcard.addSound(soundType);
        } catch (Exception ex) {
            throw new VCardParseException("SoundType (" + VCardTypeName.SOUND.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the UID type.</p>
     */
    private void parseUidType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            UidType uidType = new UidType();
            parseParamTypes(uidType, paramTypeList, value, VCardTypeName.UID);

            if (uidType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(uidType, value);
            }

            if (group != null) {
                uidType.setGroup(group);
            }

            uidType.setUid(VCardUtils.unescapeString(value));
            vcard.setUid(uidType);
        } catch (Exception ex) {
            throw new VCardParseException("UidType (" + VCardTypeName.UID.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the URL type.</p>
     */
    private void parseUrlType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            UrlType urlType = new UrlType();
            parseParamTypes(urlType, paramTypeList, value, VCardTypeName.URL);

            if (urlType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(urlType, value);
            }

            if (group != null) {
                urlType.setGroup(group);
            }

            urlType.setRawUrl(VCardUtils.unescapeString(value));
            vcard.addUrl(urlType);
        } catch (Exception ex) {
            throw new VCardParseException("UrlType (" + VCardTypeName.URL.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the CLASS type.</p>
     */
    private void parseClassType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            ClassType classType = new ClassType();
            parseParamTypes(classType, paramTypeList, value, VCardTypeName.CLASS);

            if (classType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(classType, value);
            }

            if (group != null) {
                classType.setGroup(group);
            }

            classType.setSecurityClass(VCardUtils.unescapeString(value));
            vcard.setSecurityClass(classType);
        } catch (Exception ex) {
            throw new VCardParseException("ClassType (" + VCardTypeName.CLASS.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the KEY type.</p>
     */
    private void parseKeyType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            KeyType keyType = new KeyType();
            parseParamTypes(keyType, paramTypeList, value, VCardTypeName.KEY);

            if (keyType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(keyType, value);
            }

            if (keyType.isBinary()) {
                byte[] keyBytes = Base64Wrapper.decode(value);
                keyType.setKey(keyBytes);
            } else {
                keyType.setKey(value);
            }

            if (group != null) {
                keyType.setGroup(group);
            }

            vcard.addKey(keyType);
        } catch (Exception ex) {
            throw new VCardParseException("KeyType (" + VCardTypeName.KEY.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the EXTENDED type.</p>
     */
    private void parseXtendedType(String group, String value, String typeName, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            ExtendedType extendedType = new ExtendedType();
            parseParamTypes(extendedType, paramTypeList, value, VCardTypeName.XTENDED);

            if (extendedType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(extendedType, value);
            }

            if (group != null) {
                extendedType.setGroup(group);
            }

            extendedType.setExtendedName(typeName);
            extendedType.setExtendedValue(VCardUtils.unescapeString(value));

            vcard.addExtendedType(extendedType);
        } catch (Exception ex) {
            throw new VCardParseException("ExtendedType (" + VCardTypeName.XTENDED.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the NAME type.</p>
     */
    private void parseNameType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            NameType nameType = new NameType();
            parseParamTypes(nameType, paramTypeList, value, VCardTypeName.NAME);

            if (nameType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(nameType, value);
            }

            if (group != null) {
                nameType.setGroup(group);
            }

            nameType.setName(VCardUtils.unescapeString(value));
            vcard.setName(nameType);
        } catch (Exception ex) {
            throw new VCardParseException("NameType (" + VCardTypeName.NAME.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the PROFILE type.</p>
     */
    private void parseProfileType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            ProfileType profileType = new ProfileType();
            parseParamTypes(profileType, paramTypeList, value, VCardTypeName.PROFILE);

            if (profileType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(profileType, value);
            }

            if (group != null) {
                profileType.setGroup(group);
            }

            profileType.setProfile(VCardUtils.unescapeString(value));
            vcard.setProfile(profileType);
        } catch (Exception ex) {
            throw new VCardParseException("ProfileType (" + VCardTypeName.PROFILE.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the SOURCE type.</p>
     */
    private void parseSourceType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            SourceType sourceType = new SourceType();
            parseParamTypes(sourceType, paramTypeList, value, VCardTypeName.SOURCE);

            if (sourceType.isQuotedPrintable()) {
                value = decodeQuotedPrintableValue(sourceType, value);
            }

            if (group != null) {
                sourceType.setGroup(group);
            }

            sourceType.setSource(VCardUtils.unescapeString(value));
            vcard.setSource(sourceType);
        } catch (Exception ex) {
            throw new VCardParseException("SourceType (" + VCardTypeName.SOURCE.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Parses the IMPP type.</p>
     */
    private void parseImppType(String group, String value, List<ParameterType> paramTypeList, VCardImpl vcard) throws VCardParseException {
        try {
            ImppType imppType = new ImppType();
            parseParamTypes(imppType, paramTypeList, value, VCardTypeName.IMPP);

            if (EncodingType.EIGHT_BIT.equals(imppType.getEncodingType())) {
                imppType.setUri(new URI(value));
            } else {
                throw new VCardParseException("IMPP's encoding must be 8bit.");
            }

            if (group != null) {
                imppType.setGroup(group);
            }

            vcard.addImpp(imppType);
        } catch (Exception ex) {
            throw new VCardParseException("ImppType (" + VCardTypeName.IMPP.getType() + ") [" + ex.getClass().getName() + "] " + ex.getMessage(), ex);
        }
    }

    /**
     * <p>Creates a VCardError object and sets the specified error information
     * in it and adds it to the VCard currently being parses.</p>
     */
    private void handleError(VCardImpl vcard, String errorMessage, Throwable exception, ErrorSeverity severity) {
        VCardError vError = new VCardError();
        vError.setErrorMessage(errorMessage);
        vError.setSeverity(severity);

        if (exception != null) {
            vError.setError(exception);
        }

        vcard.addError(vError);
    }

    /**
     * <p>Parses the raw parameter names and values and returns
     * a list of {@link ParameterType} objects.</p>
     *
     * @return {@link List}&lt;ParameterType&gt;
     */
    private List<ParameterType> parseParamTypesLine(List<String[]> paramTypes) {
        List<ParameterType> parameterTypes = new ArrayList<>();
        for (String[] param : paramTypes) {
            String paramName = param[0];
            String paramValue = param[1].toUpperCase().trim();
            if (paramName != null) {
                paramName = paramName.toUpperCase().trim();
                parameterTypes.add(new ParameterType(paramName, paramValue));
            } else {
                //When the parameter types are missing we try to guess what they are.
                //We really should not as it breaks RFC rules but some apps do broken exports.

                if (paramValue.equals(EncodingType.BASE64.getType())) {
                    parameterTypes.add(new ParameterType("ENCODING", paramValue));
                } else if (paramValue.equals(EncodingType.BINARY.getType())) {
                    parameterTypes.add(new ParameterType("ENCODING", paramValue));
                } else if (paramValue.equals(EncodingType.QUOTED_PRINTABLE.getType())) {
                    parameterTypes.add(new ParameterType("ENCODING", paramValue));
                } else if ("URI".equals(paramValue)) {
                    parameterTypes.add(new ParameterType("VALUE", paramValue));
                } else {
                    try {
                        //It could be a charset
                        boolean isCharset = Charset.isSupported(paramValue);
                        if (isCharset) {
                            //it is a charset and supported
                            parameterTypes.add(new ParameterType("CHARSET", paramValue));
                        } else {
                            //It is a charset but not supported
                            parameterTypes.add(new ParameterType("TYPE", paramValue));
                        }
                    } catch (Exception ex) {
                        //It is not a charset

                        /*
                         * Type special notes: The type can include the type parameter "TYPE" to
                         * specify the graphic image format type. The TYPE parameter values MUST
                         * be one of the IANA registered image formats or a non-standard image format.
                         */

                        parameterTypes.add(new ParameterType("TYPE", paramValue));
                    }
                }
            }
        }

        return parameterTypes;
    }

    private void parseParamTypes(AbstractVCardType vcardType, List<ParameterType> paramTypeList, String value, VCardTypeName typeName) throws VCardParseException {
        for (ParameterType pt : paramTypeList) {
            String nam = pt.getName();
            String val = pt.getValue();

            if ("CHARSET".equalsIgnoreCase(nam)) {
                vcardType.setCharset(val);
                value = new String(value.getBytes(), vcardType.getCharset());
            } else if ("ENCODING".equalsIgnoreCase(nam)) {
                if (EncodingType.QUOTED_PRINTABLE.getType().equalsIgnoreCase(val)) {
                    vcardType.setEncodingType(EncodingType.QUOTED_PRINTABLE);
                } else if (EncodingType.BINARY.getType().equalsIgnoreCase(val)) {
                    vcardType.setEncodingType(EncodingType.BINARY);
                } else if (EncodingType.BASE64.getType().equalsIgnoreCase(val)) {
                    vcardType.setEncodingType(EncodingType.BINARY);
                } else if (EncodingType.EIGHT_BIT.getType().equalsIgnoreCase(val)) {
                    vcardType.setEncodingType(EncodingType.EIGHT_BIT);
                } else if (EncodingType.SEVEN_BIT.getType().equalsIgnoreCase(val)) {
                    vcardType.setEncodingType(EncodingType.SEVEN_BIT);
                } else {
                    throw new VCardParseException("Invalid encoding type \"" + val + "\"");
                }
            } else if ("LANGUAGE".equalsIgnoreCase(nam)) {
                vcardType.setLanguage(val);
            } else if ("TYPE".equalsIgnoreCase(nam)) {
                switch (typeName) {
                    case PHOTO:
                    case LOGO: {
                        if (vcardType instanceof PhotoFeature) {
                            ImageMediaType mediaType = ImageMediaType.valueOf(val);
                            if (mediaType == null) {
                                mediaType = new ImageMediaType(val, val, val);
                            }

                            ((PhotoFeature) vcardType).setImageMediaType(mediaType);
                        }

                        if (vcardType instanceof LogoFeature) {
                            ImageMediaType mediaType = ImageMediaType.valueOf(val);
                            if (mediaType == null) {
                                mediaType = new ImageMediaType(val, val, val);
                            }

                            ((LogoFeature) vcardType).setImageMediaType(mediaType);
                        }

                        break;
                    }

                    case SOUND: {
                        if (vcardType instanceof SoundFeature) {
                            AudioMediaType mediaType = AudioMediaType.valueOf(val);
                            if (mediaType == null) {
                                mediaType = new AudioMediaType(val, val, val);
                            }

                            ((SoundFeature) vcardType).setAudioMediaType(mediaType);
                        }

                        break;
                    }

                    case KEY: {
                        if (vcardType instanceof KeyFeature) {
                            KeyTextType keyTextType = KeyTextType.valueOf(val);
                            if (keyTextType == null) {
                                keyTextType = new KeyTextType(val, val, val);
                            }

                            ((KeyFeature) vcardType).setKeyTextType(keyTextType);
                        }

                        break;
                    }

                    case ADR: {
                        if (vcardType instanceof AdrFeature) {
                            if (val.indexOf(',') != -1) {
                                String[] typeValueList = val.split(",");
                                for (String typeValue : typeValueList) {
                                    setAdrParamType((AdrType) vcardType, typeValue);
                                }
                            } else {
                                setAdrParamType((AdrType) vcardType, val);
                            }
                        }

                        break;
                    }

                    case LABEL: {
                        if (vcardType instanceof LabelFeature) {
                            if (val.indexOf(',') != -1) {
                                String[] typeValueList = val.split(",");
                                for (String typeValue : typeValueList) {
                                    setLabelParamType((LabelType) vcardType, typeValue);
                                }
                            } else {
                                setLabelParamType((LabelType) vcardType, val);
                            }
                        }

                        break;
                    }

                    case TEL: {
                        if (vcardType instanceof TelFeature) {
                            if (val.indexOf(',') != -1) {
                                String[] typeValueList = pt.getValue().split(",");
                                for (String typeValue : typeValueList) {
                                    setTelParamType((TelType) vcardType, typeValue);
                                }
                            } else {
                                setTelParamType((TelType) vcardType, val);
                            }
                        }

                        break;
                    }

                    case EMAIL: {
                        if (vcardType instanceof EmailFeature) {
                            if (val.indexOf(',') != -1) {
                                String[] typeValueList = pt.getValue().split(",");
                                for (String typeValue : typeValueList) {
                                    setEmailParamType((EmailType) vcardType, typeValue);
                                }
                            } else {
                                setEmailParamType((EmailType) vcardType, val);
                            }
                        }

                        break;
                    }

                    case URL: {
                        if (vcardType instanceof UrlFeature) {
                            if (val.indexOf(',') != -1) {
                                String[] typeValueList = pt.getValue().split(",");
                                for (String typeValue : typeValueList) {
                                    setUrlParamType((UrlType) vcardType, typeValue);
                                }
                            } else {
                                setUrlParamType((UrlType) vcardType, val);
                            }
                        }

                        break;
                    }

                    case IMPP: {
                        if (vcardType instanceof ImppFeature) {
                            if (val.indexOf(',') != -1) {
                                String[] typeValueList = pt.getValue().split(",");
                                for (String typeValue : typeValueList) {
                                    setImppParamType((ImppType) vcardType, typeValue);
                                }
                            } else {
                                setImppParamType((ImppType) vcardType, val);
                            }
                        }

                        break;
                    }

                    default: {
                        if (val.indexOf(',') != -1) {
                            String[] typeValueList = pt.getValue().split(",");
                            for (String typeValue : typeValueList) {
                                vcardType.addExtendedParam(new ExtendedParamType(nam, typeValue, VCardTypeName.XTENDED));
                            }
                        } else {
                            vcardType.addExtendedParam(new ExtendedParamType(nam, val, VCardTypeName.XTENDED));
                        }

                        break;
                    }

                }//switch type name
            } else if ("VALUE".equalsIgnoreCase(nam)) {
                if ("URI".equalsIgnoreCase(val)) {
                    vcardType.setEncodingType(EncodingType.EIGHT_BIT);
                } else if ("DATE".equalsIgnoreCase(val)) {
                    if (typeName == VCardTypeName.BDAY) {
                        if (vcardType instanceof BDayFeature) {
                            ((BDayFeature) vcardType).setParam(BDayParamType.DATE);
                        }
                    }
                } else if ("DATE-TIME".equalsIgnoreCase(val)) {
                    if (typeName == VCardTypeName.BDAY) {
                        if (vcardType instanceof BDayFeature) {
                            ((BDayFeature) vcardType).setParam(BDayParamType.DATE_TIME);
                        }
                    }
                } else if ("TEXT".equalsIgnoreCase(val)) {
                    if (typeName == VCardTypeName.TZ) {
                        if (vcardType instanceof TzFeature) {
                            ((TzType) vcardType).setParamType(TzParamType.TEXT);
                        }
                    }
                } else if ("UTC-OFFSET".equalsIgnoreCase(val)) {
                    if (typeName == VCardTypeName.TZ) {
                        if (vcardType instanceof TzFeature) {
                            ((TzType) vcardType).setParamType(TzParamType.UTC_OFFSET);
                        }
                    }
                } else {
                    throw new VCardParseException("Invalid value type \"" + val + "\"");
                }
            } else {
                vcardType.addExtendedParam(new ExtendedParamType(nam, val, typeName));
            }
        }
    }

    private String decodeQuotedPrintableValue(AbstractVCardType vcardType, String value) throws DecoderException, UnsupportedEncodingException {
        String decodedValue;
        if (isCharsetForced()) {
            decodedValue = QP_CODEC.decode(value, forceCharset.name());
        } else {
            if (vcardType.hasCharset()) {
                decodedValue = QP_CODEC.decode(value, vcardType.getCharset().name());
            } else {
                decodedValue = QP_CODEC.decode(value);
            }
        }

        return decodedValue;
    }

    private void setAdrParamType(AdrType adrType, String paramValue) {
        try {
            String enumParamValue = paramValue.replace("-", "_").toUpperCase();
            AdrParamType paramType = AdrParamType.valueOf(enumParamValue);
            adrType.addParam(paramType);
        } catch (IllegalArgumentException iae) {
            ExtendedParamType xParamType;
            if (paramValue.indexOf('=') != -1) {
                String[] pTmp = paramValue.split("=");
                xParamType = new ExtendedParamType(pTmp[0], pTmp[1], VCardTypeName.ADR);
            } else {
                xParamType = new ExtendedParamType(paramValue, VCardTypeName.ADR);
            }

            adrType.addExtendedParam(xParamType);
        }
    }

    private void setLabelParamType(LabelType labelType, String paramValue) {
        try {
            String enumParamValue = paramValue.replace("-", "_").toUpperCase();
            LabelParamType paramType = LabelParamType.valueOf(enumParamValue);
            labelType.addParam(paramType);
        } catch (IllegalArgumentException iae) {
            ExtendedParamType xParamType;
            if (paramValue.indexOf('=') != -1) {
                String[] pTmp = paramValue.split("=");
                xParamType = new ExtendedParamType(pTmp[0], pTmp[1], VCardTypeName.LABEL);
            } else {
                xParamType = new ExtendedParamType(paramValue, VCardTypeName.LABEL);
            }

            labelType.addExtendedParam(xParamType);
        }
    }

    private void setTelParamType(TelType telType, String paramValue) {
        try {
            String enumParamValue = paramValue.replace("-", "_").toUpperCase();
            TelParamType telParamType = TelParamType.valueOf(enumParamValue);
            telType.addParam(telParamType);
        } catch (IllegalArgumentException iae) {
            ExtendedParamType xParamType;
            if (paramValue.indexOf('=') != -1) {
                String[] pTmp = paramValue.split("=");
                xParamType = new ExtendedParamType(pTmp[0], pTmp[1], VCardTypeName.TEL);
            } else {
                xParamType = new ExtendedParamType(paramValue, VCardTypeName.TEL);
            }

            telType.addExtendedParam(xParamType);
        }
    }

    private void setEmailParamType(EmailType emailType, String paramValue) {
        try {
            String enumParamValue = paramValue.replace("-", "_").toUpperCase();
            EmailParamType emailParamType = EmailParamType.valueOf(enumParamValue);
            emailType.addParam(emailParamType);
        } catch (IllegalArgumentException iae) {
            ExtendedParamType xParamType;
            if (paramValue.indexOf('=') != -1) {
                String[] pTmp = paramValue.split("=");
                xParamType = new ExtendedParamType(pTmp[0], pTmp[1], VCardTypeName.EMAIL);
            } else {
                xParamType = new ExtendedParamType(paramValue, VCardTypeName.EMAIL);
            }

            emailType.addExtendedParam(xParamType);
        }
    }

    private void setUrlParamType(UrlType urlType, String paramValue) {
        try {
            String enumParamValue = paramValue.replace("-", "_").toUpperCase();
            UrlParamType urlParamType = UrlParamType.valueOf(enumParamValue);
            urlType.addParam(urlParamType);
        } catch (IllegalArgumentException iae) {
            ExtendedParamType xParamType;
            if (paramValue.indexOf('=') != -1) {
                String[] pTmp = paramValue.split("=");
                xParamType = new ExtendedParamType(pTmp[0], pTmp[1], VCardTypeName.URL);
            } else {
                xParamType = new ExtendedParamType(paramValue, VCardTypeName.URL);
            }

            urlType.addExtendedParam(xParamType);
        }
    }

    private void setImppParamType(ImppType imppType, String paramValue) {
        try {
            String enumParamValue = paramValue.replace("-", "_").toUpperCase();
            ImppParamType imppParamType = ImppParamType.valueOf(enumParamValue);
            imppType.addParam(imppParamType);
        } catch (IllegalArgumentException iae) {
            ExtendedParamType xParamType;
            if (paramValue.indexOf('=') != -1) {
                String[] pTmp = paramValue.split("=");
                xParamType = new ExtendedParamType(pTmp[0], pTmp[1], VCardTypeName.IMPP);
            } else {
                xParamType = new ExtendedParamType(paramValue, VCardTypeName.IMPP);
            }

            imppType.addExtendedParam(xParamType);
        }
    }

    /**
     * <p>Returns the contents of the vcard file where each line is delimited with
     * the standard java EOL char '\n' This is still a folded vcard String.</p>
     *
     * @return {@link String}
     */
    private String getContentFromFile(File vcardFile) throws IOException {
//        return getContent(new InputStreamReader(new FileInputStream(vcardFile), Charset.forName("UTF-8")));

        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(vcardFile));
        int p = (bin.read() << 8) + bin.read();
        String code;
        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }
        InputStreamReader isr = new InputStreamReader(new FileInputStream(vcardFile), Charset.forName(code));
        return getContent(isr);
    }

    /**
     * <p>Returns the contents of the vcard String where each line is delimited with
     * the standard java EOL char '\n' This is still a folded vcard String.</p>
     *
     * @return {@link String}
     */
    private String getContentFromString(String vcardString) throws IOException {
        return getContent(new StringReader(vcardString));
    }

    /**
     * <p>Reads the content of a VCard from a reader interface. This method
     * performs the following automatic operations:
     * <ul>
     * <li>Removes blank lines</li>
     * <li>Replaces CRLF end of lines with just LF (\r\n -> \n)</li>
     * <li>Detects and unfolds Quoted-Printable lines</li>
     * <li>Makes a big effort to handle badly folded Quoted-Printable lines</li>
     * </ul>
     * <p>
     * Please note that the unfolding of binary data and other lines that are not
     * encoded as quoted-printable can be done using <code>{@link VCardUtils}.unfoldVCard(String, CompatibilityMode)</code>
     * </p>
     *
     * @return {@link String}
     */
    private String getContent(Reader reader) throws IOException {
        BufferedReader br = null;
        String vcardStr;

        try {
            br = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();
            String line;
            boolean prevFolded = false;
            boolean isQuotedPrintable = false;
            boolean isQuotedPrintableEnd = false;

            while ((line = br.readLine()) != null) {
                if (!emptyLinePattern.matcher(line).matches()) {
                    //non-empty line
                    if (isQuotedPrintable && prevFolded && !isQuotedPrintableEnd) {
                        //I am expecting more quoted printable lines

                        //This is true if the current line is the start
                        //of a VCard type, regardless of what was before it.
                        if (vcardTypePattern.matcher(line).matches()) {
                            isQuotedPrintableEnd = true;
                            isQuotedPrintable = false;
                            prevFolded = false;
                            sb.append('\n');
                        }
                    }

                    if (startQpLinePattern.matcher(line).matches()) {
                        //A quoted-printable line that has a soft-line break fold
                        isQuotedPrintableEnd = false;
                        isQuotedPrintable = true;
                        prevFolded = true;
                        line = line.trim();
                        String s = line.substring(0, line.lastIndexOf('='));
                        sb.append(s);
                    } else if (middleQpLinePattern.matcher(line).matches() && isQuotedPrintable && prevFolded) {
                        if (line.endsWith("=")) {
                            //Line is folded and there is more
                            line = line.trim();
                            String s = line.substring(0, line.lastIndexOf('='));
                            sb.append(s);
                        } else {
                            //This is the last line
                            sb.append(line.trim());
                            sb.append('\n');
                            isQuotedPrintable = false;
                            prevFolded = false;
                            isQuotedPrintableEnd = true;
                        }
                    } else {
                        sb.append(line);
                        sb.append('\n');
                    }
                }
            }

            vcardStr = sb.toString();
        } finally {
            if (br != null) {
                br.close();
            }
        }

        return vcardStr;
    }
}
