package algorithm.encrypt.secure;

/**
 * 不适用任何安全加密
 * @author linling
 *
 */
public class NoSecureCodec extends BasicCodec
{

	@Override
	public byte[] encrypt(byte[] data) throws Exception
	{
		return data;
	}

	@Override
	public byte[] decrypt(byte[] data) throws Exception
	{
		return data;
	}

}
