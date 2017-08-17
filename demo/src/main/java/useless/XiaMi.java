package useless;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2017/2/12
 * Time: 11:55
 */
public class XiaMi {
    private static final String UA = "Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X; en-us) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53";

    public static void main(String[] args) throws IOException {
        for (int i = 1; i < 5; i++) {
            System.out.println(getXiaMiData(String.valueOf(i)));
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * from string URL to string content then JSON Object
     */
    private static Map<String, Object> getXiaMiData(String id) throws IOException {
        Map<String, Object> map = new HashMap<>();
        String url = "http://www.xiami.com/song/playlist/id/" + id + "/object_id/0/cat/json";

        String jsonText;
        JSONObject jsonObject;
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            jsonText = readAll(rd);
            jsonObject = new JSONObject(jsonText);
        }
        if (null == jsonObject || !jsonObject.getBoolean("status")) {
            return map;
        }

        JSONObject data = jsonObject.getJSONObject("data");
        if (null == data) {
            return map;
        }
        JSONArray trackList = data.getJSONArray("trackList");
        if (null == trackList) {
            return map;
        }
        JSONObject track = trackList.getJSONObject(0);
        String songUrl = track.getString("location");
        songUrl = xiaMiDecode(songUrl);
        String songPic = track.getString("album_pic").replace(".jpg", "_2.jpg");
        String title = track.getString("title");
        String singer = track.getString("artist");
        String lyricUrl = track.getString("lyric");

        map.put("id", id);
        map.put("songUrl", songUrl);
        map.put("songPic", songPic);
        map.put("title", title);
        map.put("singer", singer);
        map.put("lyricUrl", lyricUrl);
        return map;
    }

    private static String xiaMiDecode(String location) throws UnsupportedEncodingException {
        int _local10;
        int _local2 = Integer.parseInt(location.substring(0, 1));
        String _local3 = location.substring(1, location.length());
        double _local4 = Math.floor(_local3.length() / _local2);
        int _local5 = _local3.length() % _local2;
        String[] _local6 = new String[_local2];
        int _local7 = 0;
        while (_local7 < _local5) {
            if (_local6[_local7] == null) {
                _local6[_local7] = "";
            }
            _local6[_local7] = _local3.substring((((int) _local4 + 1) * _local7),
                    (((int) _local4 + 1) * _local7) + ((int) _local4 + 1));
            _local7++;
        }
        _local7 = _local5;
        while (_local7 < _local2) {
            _local6[_local7] = _local3
                    .substring((((int) _local4 * (_local7 - _local5)) + (((int) _local4 + 1) * _local5)),
                            (((int) _local4 * (_local7 - _local5)) + (((int) _local4 + 1) * _local5)) + (int) _local4);
            _local7++;
        }
        String _local8 = "";
        _local7 = 0;
        while (_local7 < _local6[0].length()) {
            _local10 = 0;
            while (_local10 < _local6.length) {
                if (_local7 >= _local6[_local10].length()) {
                    break;
                }
                _local8 = (_local8 + _local6[_local10].charAt(_local7));
                _local10++;
            }
            _local7++;
        }
        _local8 = URLDecoder.decode(_local8, "utf8");
        String _local9 = "";
        _local7 = 0;
        while (_local7 < _local8.length()) {
            if (_local8.charAt(_local7) == '^') {
                _local9 = (_local9 + "0");
            } else {
                _local9 = (_local9 + _local8.charAt(_local7));
            }
            _local7++;
        }
        _local9 = _local9.replace("+", " ");
        return _local9;
    }
}
