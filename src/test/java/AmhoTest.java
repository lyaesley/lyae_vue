import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AmhoTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		String base = "skmns@1566";
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
//		byte[] hash = digest.digest(base.getBytes("UTF-8"));
		byte[] hash = digest.digest(base.getBytes("ISO-8859-1"));
		StringBuffer hexString = new StringBuffer();
		
		for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        //출력
        System.out.println(hexString.toString());
		
	}

}
