package algorithm.encrypt.other;

/**
 * 加密类型
 */
public enum SecureType {
	//单向加密的用途主要是为了校验数据在传输过程中是否被修改
	/**
	 * MD5,单向加密
	 */
	MD5,
	
	/**
	 * SHA,单向加密
	 */
	SHA,

	/**
	 * DES,对称加密
	 */
	DES,
	
	/**
	 * AES,对称加密
	 */
	AES,
	
	/**
	 * RSA,非对称加密. 持有秘钥的甲方
	 */
	RSA_PRIVATE,
	
	/**
	 * RSA,非对称加密. 持有公钥的乙方
	 */
	RSA_PUBLIC
}