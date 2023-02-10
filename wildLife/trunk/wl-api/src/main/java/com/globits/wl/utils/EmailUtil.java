package com.globits.wl.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.globits.wl.dto.AnimalRequiredDto;
import com.globits.wl.dto.ForestProductsListDetailDto;
import com.globits.wl.dto.ForestProductsListDto;
import com.globits.wl.dto.ProductTargetDto;
import com.globits.wl.dto.UserAttachmentsDto;
import com.globits.wl.service.UserAttachmentsService;

@Component
public class EmailUtil {
	
	
	private static UserAttachmentsService userAttachmentsService;
	private static Environment env;
	
	@Autowired
	public EmailUtil(UserAttachmentsService userAttachmentsService, Environment env) {
		EmailUtil.userAttachmentsService=userAttachmentsService;
		EmailUtil.env=env;
	}
	
	
	public static enum ResultChangePassword{
		Error(0),						//	Lỗi ex
		UsernameIsNull(1),				//	username bị null
		UserIsNull(2),					//	Tài khoản bị null
		EmailDoesNotMatchUsername(3),	//	Email không khớp với username
		LinkIsExpired(4),				//Liên kết đã hết hạn
		LinkIsBrokenOrExpired(5),		//Liên kết bị lỗi hoặc hết hạn
		LocationIsNull(6),				//location null
		PhoneNumberNotValided(7);		//Số điện thoại không trùng khớp
		
		private int value;    
		
		private ResultChangePassword(int value) {
		    this.value = value;
		}
	
		public int getValue() {
			return value;
		}
	}
	public static String encrypt(String strToEncrypt, String myKey) {
	      try {
	            MessageDigest sha = MessageDigest.getInstance("SHA-1");
	            byte[] key = myKey.getBytes("UTF-8");
	            key = sha.digest(key);
	            key = Arrays.copyOf(key, 16);
	            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
	            /*String encrypt = Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
	            return URLEncoder.encode(encrypt, "UTF-8");*/
	      } catch (Exception e) {
	            System.out.println(e.toString());
	      }
	      return null;
	}
	
	public static String decrypt(String strToDecrypt, String myKey) {
	      try {
	    	  MessageDigest sha = MessageDigest.getInstance("SHA-1");
	            byte[] key = myKey.getBytes("UTF-8");
	            key = sha.digest(key);
	            key = Arrays.copyOf(key, 16);
	            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
	            cipher.init(Cipher.DECRYPT_MODE, secretKey);
	            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	            /*String decrypt = URLDecoder.decode(strToDecrypt, "UTF-8");
	            return new String(cipher.doFinal(Base64.getDecoder().decode(decrypt)));*/
	      } catch (Exception e) {
	            System.out.println(e.toString());
	      }
	      return null;
	}
	
	public static String getMd5(String input) 
  { 
      try { 

          // Static getInstance method is called with hashing MD5 
          MessageDigest md = MessageDigest.getInstance("MD5"); 

          // digest() method is called to calculate message digest 
          //  of an input digest() return array of byte 
          byte[] messageDigest = md.digest(input.getBytes()); 

          // Convert byte array into signum representation 
          BigInteger no = new BigInteger(1, messageDigest); 

          // Convert message digest into hex value 
          String hashtext = no.toString(16); 
          while (hashtext.length() < 32) { 
              hashtext = "0" + hashtext; 
          } 
          return hashtext; 
      }  
      // For specifying wrong message digest algorithms 
      catch (NoSuchAlgorithmException e) { 
          throw new RuntimeException(e); 
      } 
  } 
	public static final String URL_PAGE_CHANGE_PASSWORD ="/#/change_password/path?key=";
	public static final Integer EXPRIED_TIME_CHANGE_PASSWORD = 15;
	public static boolean sendEmail(String host, String toEmail, String subject, String body) {
		try {
			System.out.println("SimpleEmail Start");

//			final String username = "globits.service@gmail.com"; //requires valid gmail id
//			final String password = "ctet2009"; // correct password for gmail id
			
//			final String username = "khoadv91@gmail.com"; //requires valid gmail id
//			final String password = "khoa29896"; // correct password for gmail id
			
			//tran huu dat them email start
			final String username = "globits123456@gmail.com"; //requires valid gmail id
//			final String password = "datptit09"; // correct password for gmail id
			final String password = "yvtioqkrioeirkzr"; // password application
			//tran huu dat them email end
			System.out.println("TLSEmail Start");
			Properties prop = new Properties();
			prop.put("mail.smtp.host", "smtp.gmail.com");
	        prop.put("mail.smtp.port", "465");
	        prop.put("mail.smtp.auth", "true");
	        prop.put("mail.smtp.user", username);
	        prop.put("mail.smtp.password", password);
	        //tran huu dat them
//	        prop.put("mail.smtp.starttls.enable", "true");
//	        prop.put("mail.smtp.starttls.required", "true");
	        prop.put("mail.smtp.startssl.enable", "true");
	        prop.put("mail.smtp.startssl.required", "true");
	        //tran huu dat them
	        prop.put("mail.smtp.socketFactory.port", "465");
	        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        
	        Session session = Session.getInstance(prop,
	                new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(username, password);
	                    }
	                });

			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress("no_reply@example.com", host));

			msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

			msg.setSubject(subject, "UTF-8");

			msg.setText(body, "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			System.out.println("Message is ready");
			Transport.send(msg);

			System.out.println("EMail Sent Successfully!!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
			return false;
		}
	}
	
	public static boolean sendEmailNotification(String host, String[] toEmail, String subject, Object body) {
		try {
			System.out.println("SimpleEmail Start");

//			final String username = "globits.service@gmail.com"; //requires valid gmail id
//			final String password = "ctet2009"; // correct password for gmail id
			
//			final String username = "khoadv91@gmail.com"; //requires valid gmail id
//			final String password = "khoa29896"; // correct password for gmail id
			
			//tran huu dat them email start
			final String username = "globits123456@gmail.com"; //requires valid gmail id
//			final String password = "datptit09"; // correct password for gmail id
			final String password = "yvtioqkrioeirkzr"; // password application
			//tran huu dat them email end
			System.out.println("TLSEmail Start");
			Properties prop = new Properties();
			prop.put("mail.smtp.host", "smtp.gmail.com");
	        prop.put("mail.smtp.port", "465");
	        prop.put("mail.smtp.auth", "true");
	        prop.put("mail.smtp.user", username);
	        prop.put("mail.smtp.password", password);
	        //tran huu dat them
//	        prop.put("mail.smtp.starttls.enable", "true");
//	        prop.put("mail.smtp.starttls.required", "true");
	        prop.put("mail.smtp.startssl.enable", "true");
	        prop.put("mail.smtp.startssl.required", "true");
	        //tran huu dat them
	        prop.put("mail.smtp.socketFactory.port", "465");
	        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        
	        Session session = Session.getInstance(prop,
	                new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(username, password);
	                    }
	                });

			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			msg.setFrom(new InternetAddress("no_reply@example.com", host, "UTF-8"));
//			msg.setFrom(new InternetAddress("no_reply@example.com", host));

			msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

			msg.setSubject(subject, "UTF-8");

			//tran huu dat them giao dien html
			String htmltemplate="";
			if(body instanceof AnimalRequiredDto) {
				WLConstant.FolderPath = env.getProperty("server.port");
				String url="";
				switch(WLConstant.FolderPath) {
				case "8082":
					url="http://wldemo.globits.net/";
					break;
				case "8083":
					url="http://wl.globits.net/";
					break;
				default:
					url="http://wldemo.globits.net/";
					break;
				}
				AnimalRequiredDto animal=(AnimalRequiredDto) body;
				StringBuilder listProductTargets= new StringBuilder();
				for(ProductTargetDto product : animal.getAnimalProductTargets()) {
					listProductTargets.append(product.getName()+" ");
				};
				String namePerson="";
				String phoneNumber="";
				String email="";
				if(animal.getUser()!=null && animal.getUser().getPerson()!=null) {
					namePerson= animal.getUser().getPerson().getDisplayName();
				}
				if(animal.getUser()!=null && animal.getUser().getPerson()!=null) {
					phoneNumber= animal.getUser().getPerson().getPhoneNumber();
				}
				if(animal.getUser()!=null && animal.getUser().getEmail()!=null) {
					email= animal.getUser().getEmail();
				}
				htmltemplate="<h1>Có yêu cầu thềm loài mới</h1>\r\n"
						+"<h2>Thông tin chi tiết</h2>\r\n"
						+ "<table>\r\n"
						+ "        <tr>\r\n"
						+ "            <td><b>Tên tiếng việt</b></td>\r\n"
						+ "            <td>"+(animal.getName()==null?"":animal.getName())+"</td>\r\n"
						+ "            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\r\n"
						+ "            <td><b>Tên khoa học<b></td>\r\n"
						+ "            <td>"+(animal.getScienceName()==null?"":animal.getScienceName())+"</td>\r\n"
						+ "        </tr>\r\n"
						+ "        <tr>\r\n"
						+ "            <td><b>Tên tiếng anh</b></td>\r\n"
						+ "            <td>"+(animal.getEnName()==null?"":animal.getEnName())+"</td>\r\n"
						+ "            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\r\n"
						+ "            <td><b>Nhóm động vật<b></td>\r\n"
						+ "            <td>"+(animal.getAniGroup()==null?"":animal.getAniGroup())+"</td>\r\n"
						+ "        </tr>\r\n"
						+ "        <tr>\r\n"
						+ "            <td><b>Lớp động vật</b></td>\r\n"
						+ "            <td>"+(animal.getAnimalClass()==null?"":animal.getAnimalClass())+"</td>\r\n"
						+ "            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\r\n"
						+ "            <td><b>Bộ động vật</b></td>\r\n"
						+ "            <td>"+(animal.getOrdo()==null?"":animal.getOrdo())+"</td>\r\n"
						+ "        </tr>\r\n"
						+ "        <tr>\r\n"
						+ "            <td><b>Họ</b></td>\r\n"
						+ "            <td>"+(animal.getFamily()==null?"":animal.getFamily())+"</td>\r\n"
						+ "            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\r\n"
						+ "            <td><b>Phân nhóm theo phụ lục CITES</b></td>\r\n"
						+ "            <td>"+(animal.getCites()==null?"":animal.getCites())+"</td>\r\n"
						+ "        </tr>\r\n"
						+ "        <tr>\r\n"
						+ "            <td><b>Phân nhóm theo nghị định 64</b></td>\r\n"
						+ "            <td>"+(animal.getVnlist()==null?"":animal.getVnlist())+"</td>\r\n"
						+ "            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\r\n"
						+ "            <td><b>Phân nhóm theo nghị định 06</b></td>\r\n"
						+ "            <td>"+(animal.getVnlist06()==null?"":animal.getVnlist06())+"</td>\r\n"
						+ "        </tr>\r\n"
						
//						+ "        <tr>\r\n"
//						+ "            <td><b>Nguồn gốc</b></td>\r\n"
//						+ "            <td>"+(animal.getOriginalDto()==null?"":animal.getOriginalDto().getName())+"</td>\r\n"
//						+ "            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\r\n"
//						+ "            <td><b>Loại động vật</b></td>\r\n"
//						+ "            <td>"+(animal.getTypeDto()==null?"":animal.getTypeDto().getName())+"</td>\r\n"
//						+ "        </tr>\r\n"
						+ "        <tr>\r\n"
						+ "            <td><b>Nhóm ưu tiên bảo vệ</b></td>\r\n"
						+ "            <td>"+(animal.getAnimalGroup()==null?"":animal.getAnimalGroup())+"</td>\r\n"
						+ "            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\r\n"
						+ "            <td><b>Hình thức sinh sản</b></td>\r\n"
						+ "            <td>"+(animal.getReproductionForm()==null?"":(animal.getReproductionForm()==1?"Đẻ con":"Đẻ trứng"))+"</td>\r\n"
						+ "        </tr>\r\n"
						+ "        <tr>\r\n"
						+ "            <td><b> Mô tả:</b>"+(animal.getDescription()==null?"":animal.getDescription())+"</td>\r\n"
						+ "        </tr>\r\n"
						+ "        \r\n"
						+ "    </table>\r\n"
						+ "<p>Thông tin người yêu cầu: <i>"+(namePerson==null?"":namePerson)+"</i></p>\r\n"
						+ "<p>Số điện thoại: <i>"+(phoneNumber==null?"":phoneNumber)+"</i></p>\r\n"
						+ "<p>Email: <i>"+(email==null?"":email)+"</i></p>"
						+ "<p>Chuyển đến trang phê duyệt: <i>"+"<a href='"+url+"'>Phản hồi yêu cầu thêm loài</a>"+"</i></p>"
						;
			}
			
			//tran huu dat them dao dien html
			msg.setText(htmltemplate.toString(), "UTF-8","html");

			msg.setSentDate(new Date());
			//tran huu dat danh sach cac email
			StringBuilder stringemailBuilder= new StringBuilder();
			for(int i=0;i<toEmail.length;i++) {
				if(i==toEmail.length-1) {
					stringemailBuilder.append(toEmail[i]);
				}else {
					stringemailBuilder.append(toEmail[i]+",");
				}
			}
			System.out.print(stringemailBuilder.toString());
			//tran huu dat danh sach cac email
			msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(stringemailBuilder.toString(), false));//gui nhieu email
			System.out.println("Message is ready");
			Transport.send(msg);

			System.out.println("EMail Sent Successfully!!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
			return false;
		}
	}
	
	
	public static boolean sendEmailNotificationAccept(String host, String[] toEmail, String subject, Object body) {
		try {
			System.out.println("SimpleEmail Start");

//			final String username = "globits.service@gmail.com"; //requires valid gmail id
//			final String password = "ctet2009"; // correct password for gmail id
			
//			final String username = "khoadv91@gmail.com"; //requires valid gmail id
//			final String password = "khoa29896"; // correct password for gmail id
			
			//tran huu dat them email start
			final String username = "globits123456@gmail.com"; //requires valid gmail id
//			final String password = "datptit09"; // correct password for gmail id
			final String password = "yvtioqkrioeirkzr"; // password application
			//tran huu dat them email end
			System.out.println("TLSEmail Start");
			Properties prop = new Properties();
			prop.put("mail.smtp.host", "smtp.gmail.com");
	        prop.put("mail.smtp.port", "465");
	        prop.put("mail.smtp.auth", "true");
	        prop.put("mail.smtp.user", username);
	        prop.put("mail.smtp.password", password);
	        //tran huu dat them
//	        prop.put("mail.smtp.starttls.enable", "true");
//	        prop.put("mail.smtp.starttls.required", "true");
	        prop.put("mail.smtp.startssl.enable", "true");
	        prop.put("mail.smtp.startssl.required", "true");
	        //tran huu dat them
	        prop.put("mail.smtp.socketFactory.port", "465");
	        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        
	        Session session = Session.getInstance(prop,
	                new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(username, password);
	                    }
	                });

			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress("no_reply@example.com", host,"UTF-8"));

			msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

			msg.setSubject(subject, "UTF-8");

			//tran huu dat them giao dien html
			String htmltemplate="";
			if(body instanceof AnimalRequiredDto) {
				AnimalRequiredDto animal=(AnimalRequiredDto) body;
				StringBuilder listProductTargets= new StringBuilder();
				for(ProductTargetDto product : animal.getAnimalProductTargets()) {
					listProductTargets.append(product.getName()+" ");
				};
				if(animal.getAnimalRequiredStatus()==2) {
					htmltemplate="<h1>Yêu cầu thêm loài: '"+ (animal.getName()==null?"":animal.getName())+ "' đã được xác nhận</h1>\r\n";
				}
				if(animal.getAnimalRequiredStatus()==3) {
					htmltemplate="<h1>Yêu cầu thêm loài: ' "+ (animal.getName()==null?"":animal.getName())+ "' đã bị từ chối</h1>\r\n"
							+ "<h1>Lí do:  "+ (animal.getFeedBack()==null?"":animal.getFeedBack())+ "</h1>\r\n";
				}
			}
			
			
//			if(body instanceof AnimalRequiredDto) {
//				AnimalRequiredDto animal=(AnimalRequiredDto) body;
//				StringBuilder listProductTargets= new StringBuilder();
//				for(ProductTargetDto product : animal.getAnimalProductTargets()) {
//					listProductTargets.append(product.getName()+" ");
//				};
//				htmltemplate="<h1>Có yêu cầu thềm loài mới</h1>\r\n"
//						+"<h2>Thông tin chi tiết</h2>\r\n"
//						+ "<table>\r\n"
//						+ "        <tr>\r\n"
//						+ "            <td><b>Tên tiếng việt</b></td>\r\n"
//						+ "            <td>"+animal.getName()+"</td>\r\n"
//						+ "            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\r\n"
//						+ "            <td><b>Tên khoa học<b></td>\r\n"
//						+ "            <td>"+animal.getScienceName()+"</td>\r\n"
//						+ "        </tr>\r\n"
//						+ "        <tr>\r\n"
//						+ "            <td><b>Tên tiếng anh</b></td>\r\n"
//						+ "            <td>"+animal.getEnName()+"</td>\r\n"
//						+ "            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\r\n"
//						+ "            <td><b>Nhóm động vật<b></td>\r\n"
//						+ "            <td>"+animal.getAniGroup()+"</td>\r\n"
//						+ "        </tr>\r\n"
//						+ "        <tr>\r\n"
//						+ "            <td><b>Lớp động vật</b></td>\r\n"
//						+ "            <td>"+animal.getAnimalClass()+"</td>\r\n"
//						+ "            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\r\n"
//						+ "            <td><b>Bộ động vật</b></td>\r\n"
//						+ "            <td>"+animal.getOrdo()+"</td>\r\n"
//						+ "        </tr>\r\n"
//						+ "        <tr>\r\n"
//						+ "            <td><b>Họ</b></td>\r\n"
//						+ "            <td>"+animal.getFamily()+"</td>\r\n"
//						+ "            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\r\n"
//						+ "            <td><b>Phân nhóm theo phụ lục CITES</b></td>\r\n"
//						+ "            <td>"+animal.getCites()+"</td>\r\n"
//						+ "        </tr>\r\n"
//						+ "        <tr>\r\n"
//						+ "            <td><b>Phân nhóm theo nghị định 64</b></td>\r\n"
//						+ "            <td>"+animal.getVnlist()+"</td>\r\n"
//						+ "            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\r\n"
//						+ "            <td><b>Phân nhóm theo nghị định 06</b></td>\r\n"
//						+ "            <td>"+animal.getVnlist06()+"</td>\r\n"
//						+ "        </tr>\r\n"
//						+ "        <tr>\r\n"
//						+ "            <td><b>Nguồn gốc</b></td>\r\n"
//						+ "            <td>"+animal.getOriginalDto().getName()+"</td>\r\n"
//						+ "            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\r\n"
//						+ "            <td><b>Loại động vật</b></td>\r\n"
//						+ "            <td>"+animal.getTypeDto().getName()+"</td>\r\n"
//						+ "        </tr>\r\n"
//						+ "        <tr>\r\n"
//						+ "            <td><b>Mục đích nuôi</b></td>\r\n"
//						+ "            <td>"+listProductTargets.toString()+"</td>\r\n"
//						+ "            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\r\n"
//						+ "            <td><b>Hình thức sinh sản</b></td>\r\n"
//						+ "            <td>"+(animal.getReproductionForm()==1?"Đẻ con":"Đẻ trứng")+"</td>\r\n"
//						+ "        </tr>\r\n"
//						+ "        <tr>\r\n"
//						+ "            <td><b> Mô tả:</b>"+animal.getDescription()+"</td>\r\n"
//						+ "        </tr>\r\n"
//						+ "        \r\n"
//						+ "    </table>";
//			}
			
			//tran huu dat them dao dien html
			msg.setText(htmltemplate.toString(), "UTF-8","html");

			msg.setSentDate(new Date());
			//tran huu dat danh sach cac email
			StringBuilder stringemailBuilder= new StringBuilder();
			for(int i=0;i<toEmail.length;i++) {
				if(i==toEmail.length-1) {
					stringemailBuilder.append(toEmail[i]);
				}else {
					stringemailBuilder.append(toEmail[i]+",");
				}
			}
			System.out.print(stringemailBuilder.toString());
			//tran huu dat danh sach cac email
			msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(stringemailBuilder.toString(), false));//gui nhieu email
			System.out.println("Message is ready");
			Transport.send(msg);

			System.out.println("EMail Sent Successfully!!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
			return false;
		}
	}
	
	public static boolean sendEmailForestProduct(String host, String[] toEmail, String subject, Object body) {
		try {
			System.out.println("SimpleEmail Start");

//			final String username = "globits.service@gmail.com"; //requires valid gmail id
//			final String password = "ctet2009"; // correct password for gmail id
			
//			final String username = "khoadv91@gmail.com"; //requires valid gmail id
//			final String password = "khoa29896"; // correct password for gmail id
			
			//tran huu dat them email start
			final String username = "globits123456@gmail.com"; //requires valid gmail id
//			final String password = "datptit09"; // correct password for gmail id
			final String password = "yvtioqkrioeirkzr"; // password application
			//tran huu dat them email end
			System.out.println("TLSEmail Start");
			Properties prop = new Properties();
			prop.put("mail.smtp.host", "smtp.gmail.com");
	        prop.put("mail.smtp.port", "465");
	        prop.put("mail.smtp.auth", "true");
	        prop.put("mail.smtp.user", username);
	        prop.put("mail.smtp.password", password);
	        //tran huu dat them
//	        prop.put("mail.smtp.starttls.enable", "true");
//	        prop.put("mail.smtp.starttls.required", "true");
	        prop.put("mail.smtp.startssl.enable", "true");
	        prop.put("mail.smtp.startssl.required", "true");
	        //tran huu dat them
	        prop.put("mail.smtp.socketFactory.port", "465");
	        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        
	        Session session = Session.getInstance(prop,
	                new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(username, password);
	                    }
	                });

			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress("no_reply@example.com", host,"UTF-8"));

			msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));
	
			msg.setSubject(subject, "UTF-8");

			//tran huu dat them giao dien html
			String htmltemplate="";
			if(body instanceof ForestProductsListDto) {
				ForestProductsListDto forestProductsListDto=(ForestProductsListDto) body;
				String strDateFormat = "dd-MM-yyyy";
				SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
				String organizationName="";
				String numberPhoneOffice="";
				String organizationAddress="";
				String namePerson="";
				String email="";
				String details="";
				
				if(forestProductsListDto.getConfirmByUser()!=null && forestProductsListDto.getConfirmByUser().getId()!=null) {
					List<UserAttachmentsDto> userAttachs= new ArrayList<UserAttachmentsDto>();
					userAttachs=	userAttachmentsService.getByUserId(forestProductsListDto.getConfirmByUser().getId());
					if(userAttachs!=null && userAttachs.size()>0) {
						UserAttachmentsDto userAttach= userAttachs.get(0);
						if(userAttach!=null) {
							organizationName+=userAttach.getOrganizationName();
							numberPhoneOffice+=userAttach.getNumberPhoneOffice();
							organizationAddress+=userAttach.getOrganizationAddress();
							if(forestProductsListDto.getConfirmByUser().getPerson()!=null) {
								namePerson+=forestProductsListDto.getConfirmByUser().getPerson().getDisplayName();
							}
							
							email+=forestProductsListDto.getConfirmByUser().getEmail();
						}
					}
				}
				String detailsFooter="";
				int index=1;
				if(forestProductsListDto.getDetails()!=null && forestProductsListDto.getDetails().size()>0) {
					
					for(ForestProductsListDetailDto element: forestProductsListDto.getDetails() ) {
						details+="        <tr>\r\n"
								+ "            <td style=\"border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
								+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>"+index+"</span></p>\r\n"
								+ "            </td>\r\n"
								+ "            <td style=\"border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
								+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>"+element.getAnimal().getName()+"</span></p>\r\n"
								+ "            </td>\r\n"
								+ "            <td style=\"border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
								+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>"+element.getAnimal().getScienceName()+"</span></p>\r\n"
								+ "            </td>\r\n"
								+ "            <td style=\"border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
								+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>"+element.getAnimal().getAnimalGroup()+"</span></p>\r\n"
								+ "            </td>\r\n"
								+ "            <td style=\"border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
								+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>"+(element.getCode()==null?"":element.getCode())+"</span></p>\r\n"
								+ "            </td>\r\n"
								+ "            <td style=\"border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
								+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>"+(element.getTotal()!=null?element.getTotal().toString():"")+"</span></p>\r\n"
								+ "            </td>\r\n"
								+ "            <td style=\"border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
								+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>"+(element.getAmount()!=null?element.getAmount().toString():"")+"</span></p>\r\n"
								+ "            </td>\r\n"
								+ "            <td style=\"border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
								+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>"+(element.getUnit()==null?"":element.getUnit())+"</span></p>\r\n"
								+ "            </td>\r\n"
								+ "            <td style=\"border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
								+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>"+(element.getNote()==null?"":element.getNote())+"</span></p>\r\n"
								+ "            </td>\r\n"
								+ "        </tr>\r\n";
						index+=1;
						detailsFooter+=element.getTotal()!=null?element.getTotal().toString():"0";
						detailsFooter+=" con "+element.getAnimal().getName();
						detailsFooter+="(";
						if(element.getMale()!=null) {
							detailsFooter+=element.getMale().toString()+" Đực, ";
						}else {
							detailsFooter+="0 Đực, ";
						}
						if(element.getFemale()!=null) {
							detailsFooter+=element.getFemale().toString()+" Cái)";
						}else {
							detailsFooter+="0 Cái)";
						}
						if(element.getNote()!=null) {
							detailsFooter+=", "+element.getNote()+".";
						}else {
							detailsFooter+=".";
						}
					}
					
				}
				
				htmltemplate="<p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><strong><span style='font-size:16px;line-height:107%;font-family:\"Times New Roman\",serif;'>CỘNG H&Ograve;A X&Atilde; HỘI CHỦ NGHĨA VIỆT NAM<br>&nbsp;Độc lập - Tự do - Hạnh ph&uacute;c<br>&nbsp;---------------</span></strong></p>\r\n"
						+ "<p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:right;'><span style='font-size:11px;line-height:107%;font-family:\"Times New Roman\",serif;'>Tờ số:...../Tổng số tờ .....<br>&nbsp;</span></p>\r\n"
						+ "<p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><strong><span style='font-size:21px;line-height:107%;font-family:\"Times New Roman\",serif;'>BẢNG K&Ecirc; L&Acirc;M SẢN</span></strong></p>\r\n"
						+ "<p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><em><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>(&Aacute;p dụng đối với động vật rừng; bộ phận, dẫn xuất của động vật rừng)<br>&nbsp;Số: &hellip;./&hellip;.(1)<br>&nbsp;</span></em></p>\r\n"
						+ "<p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;'><strong><span style='font-size:16px;line-height:107%;font-family:\"Times New Roman\",serif;'>Th&ocirc;ng tin chung:</span></strong></p>\r\n"
						+ "<p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;'><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>T&ecirc;n chủ l&acirc;m sản:"+ forestProductsListDto.getFarm().getName()+" <br>&nbsp;Giấy đăng k&yacute; kinh doanh/m&atilde; số doanh nghiệp (đối với doanh nghiệp): &nbsp;"+(forestProductsListDto.getFarm().getNewRegistrationCode()==null?" .............":forestProductsListDto.getFarm().getNewRegistrationCode())+"<br>&nbsp;Địa chỉ:"+( forestProductsListDto.getFarm().getAdressDetail()==null?"............":forestProductsListDto.getFarm().getAdressDetail())+"<br>&nbsp;Số điện thoại li&ecirc;n hệ:"+ (forestProductsListDto.getFarm().getOwnerPhoneNumber()==null?"..........":forestProductsListDto.getFarm().getOwnerPhoneNumber())+"<br>&nbsp;Nguồn gốc l&acirc;m sản (2): "+ (forestProductsListDto.getOriginal()==null?"...........":forestProductsListDto.getOriginal())+"<br>&nbsp;Số h&oacute;a đơn k&egrave;m theo (nếu c&oacute;):"+(forestProductsListDto.getInvoiceCode()==null?"........":forestProductsListDto.getInvoiceCode())+"; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Ng&agrave;y:"+(forestProductsListDto.getInvoiceDate()==null?".......":dateFormat.format(forestProductsListDto.getInvoiceDate()))+"<br>&nbsp;Phương tiện vận chuyển (nếu c&oacute;): "+ (forestProductsListDto.getVehicle()==null?".........":forestProductsListDto.getVehicle())+ " &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;biển số/số hiệu phương tiện: &nbsp;"+ (forestProductsListDto.getVehicleRegistrationPlate()==null? "........." : forestProductsListDto.getVehicleRegistrationPlate())+
						"<br>&nbsp;Thời gian vận chuyển: "+ (forestProductsListDto.getTransportDuration()==null?".........":forestProductsListDto.getTransportDuration())+"ng&agrave;y;&nbsp;&nbsp;Từ Ng&agrave;y: "+ (forestProductsListDto.getTransportStart()==null?"........":dateFormat.format(forestProductsListDto.getTransportStart()))+" Đến Ng&agrave;y: "+(forestProductsListDto.getTransportEnd()==null?".......":dateFormat.format(forestProductsListDto.getTransportEnd()))+"<br>&nbsp;Vận chuyển từ: "+(forestProductsListDto.getAdministrativeUnitFrom()==null?"........":forestProductsListDto.getAdministrativeUnitFrom().getName()+", "+forestProductsListDto.getAdministrativeUnitFrom().getParentDto().getName())+(forestProductsListDto.getFarm().getAdressDetail()==null?"":"("+forestProductsListDto.getFarm().getAdressDetail()+")")+" &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Đến: "+ (forestProductsListDto.getAdministrativeUnitTo()==null?"........":forestProductsListDto.getAdministrativeUnitTo().getName()+", "+forestProductsListDto.getAdministrativeUnitTo().getParentDto().getName())+(forestProductsListDto.getBuyerDetailAddress()==null?"":"("+forestProductsListDto.getBuyerDetailAddress()+")")+"<br>&nbsp;</span></p>\r\n"
						+ "<table style=\"margin-left:.5pt;border-collapse:collapse;border:none;\">\r\n"
						+ "    <tbody>\r\n"
						+ "        <tr>\r\n"
						+ "            <td rowspan=\"2\" style=\"border: 1pt solid windowtext;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
						+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><strong><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>TT</span></strong></p>\r\n"
						+ "            </td>\r\n"
						+ "            <td colspan=\"2\" style=\"border-top: 1pt solid windowtext;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-image: initial;border-left: none;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
						+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><strong><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>T&ecirc;n lo&agrave;i</span></strong></p>\r\n"
						+ "            </td>\r\n"
						+ "            <td rowspan=\"2\" style=\"border-top: 1pt solid windowtext;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-image: initial;border-left: none;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
						+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><strong><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>Nh&oacute;m lo&agrave;i</span></strong></p>\r\n"
						+ "            </td>\r\n"
						+ "            <td rowspan=\"2\" style=\"border-top: 1pt solid windowtext;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-image: initial;border-left: none;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
						+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><strong><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>Số hiệu nh&atilde;n đ&aacute;nh dấu (nếu c&oacute;)</span></strong></p>\r\n"
						+ "            </td>\r\n"
						+ "            <td rowspan=\"2\" style=\"border-top: 1pt solid windowtext;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-image: initial;border-left: none;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
						+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><strong><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>Số lượng</span></strong></p>\r\n"
						+ "            </td>\r\n"
						+ "            <td rowspan=\"2\" style=\"border-top: 1pt solid windowtext;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-image: initial;border-left: none;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
						+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><strong><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>Trọng lượng</span></strong></p>\r\n"
						+ "            </td>\r\n"
						+ "            <td rowspan=\"2\" style=\"border-top: 1pt solid windowtext;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-image: initial;border-left: none;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
						+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><strong><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>Đơn vị t&iacute;nh</span></strong></p>\r\n"
						+ "            </td>\r\n"
						+ "            <td rowspan=\"2\" style=\"border-top: 1pt solid windowtext;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-image: initial;border-left: none;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
						+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><strong><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>Ghi ch&uacute;</span></strong></p>\r\n"
						+ "            </td>\r\n"
						+ "        </tr>\r\n"
						+ "        <tr>\r\n"
						+ "            <td style=\"border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
						+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><strong><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>T&ecirc;n phổ th&ocirc;ng</span></strong></p>\r\n"
						+ "            </td>\r\n"
						+ "            <td style=\"border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
						+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><strong><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>T&ecirc;n khoa học</span></strong></p>\r\n"
						+ "            </td>\r\n"
						+ "        </tr>\r\n"
						+details
						+ "    </tbody>\r\n"
						+ "</table>\r\n"
						+ "<p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:normal;font-size:15px;font-family:\"Calibri\",sans-serif;'><span style='font-size:13px;font-family:\"Times New Roman\",serif;color:white;'><br></span><span style='font-size:13px;font-family:\"Times New Roman\",serif;'>Tổng số lượng v&agrave; trọng&nbsp; lượng từng lo&agrave;i động vật rừng, bộ phận v&agrave; dẫn xuất của ch&uacute;ng c&oacute; trong bảng k&ecirc;:"+detailsFooter+"<br>&nbsp;</span></p>\r\n"
						+ "<table style=\"border: none;margin-left:.5pt;border-collapse:collapse;\">\r\n"
						+ "    <tbody>\r\n"
						+ "        <tr>\r\n"
						+ "            <td style=\"width: 300pt;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
						+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><em><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>.........Ng&agrave;y........th&aacute;ng......năm 20&hellip;........</span></em></p>\r\n"
						+ "            </td>\r\n"
						+ "            <td style=\"width: 300pt;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
						+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><em><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>.........Ng&agrave;y........th&aacute;ng......năm 20&hellip;........</span></em></p>\r\n"
						+ "            </td>\r\n"
						+ "        </tr>\r\n"
						+ "        <tr>\r\n"
						+ "            <td style=\"width: 300pt;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
						+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><strong><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>X&Aacute;C NHẬN CỦA CƠ QUAN KIỂM L&Acirc;M SỞ TẠI</span></strong></p>\r\n"
						+ "            </td>\r\n"
						+ "            <td style=\"width: 300pt;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
						+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><strong><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>TỔ CHỨC, C&Aacute; NH&Acirc;N LẬP BẢNG K&Ecirc; L&Acirc;M SẢN</span></strong></p>\r\n"
						+ "            </td>\r\n"
						+ "        </tr>\r\n"
						+ "        <tr>\r\n"
						+ "            <td style=\"width: 300pt;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
						+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>V&agrave;o sổ số: &hellip;/&hellip;</span></p>\r\n"
						+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><em><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>(Người c&oacute; thẩm quyền k&yacute;, ghi r&otilde; họ t&ecirc;n, đ&oacute;ng dấu)</span></em></p>\r\n"
						+ "            </td>\r\n"
						+ "            <td style=\"width: 300pt;padding: 0in 0.5pt;vertical-align: top;\">\r\n"
						+ "                <p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;text-align:center;'><em><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>(K&yacute;, ghi r&otilde; họ t&ecirc;n, đ&oacute;ng dấu đối với tổ chức; k&yacute; ghi r&otilde; họ t&ecirc;n đối với c&aacute; nh&acirc;n)</span></em></p>\r\n"
						+ "            </td>\r\n"
						+ "        </tr>\r\n"
						+ "    </tbody>\r\n"
						+ "</table>\r\n"
						+ "<p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>"
						+ "<p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;'><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>Tên cơ quan: "+(organizationName==null?"":organizationName)+" <br></p>"
						+ "<p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;'><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>Địa chỉ cơ quan: "+(organizationAddress==null?"":organizationAddress)+" <br></p>"
						+ "<p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;'><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>Số điện thoại: "+(numberPhoneOffice==null?"":numberPhoneOffice)+" <br></p>"
						+ "<p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;'><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>Người gửi:  "+(namePerson==null?"":namePerson)+" <br></p>"
						+ "<p style='margin-top:0in;margin-right:0in;margin-bottom:8.0pt;margin-left:0in;line-height:107%;font-size:15px;font-family:\"Calibri\",sans-serif;'><span style='font-size:13px;line-height:107%;font-family:\"Times New Roman\",serif;'>Email: "+(email==null?"":email)+" <br></p>";
			}
			
			//tran huu dat them dao dien html
			msg.setText(htmltemplate.toString(), "UTF-8","html");

			msg.setSentDate(new Date());
			//tran huu dat danh sach cac email
			StringBuilder stringemailBuilder= new StringBuilder();
			for(int i=0;i<toEmail.length;i++) {
				if(i==toEmail.length-1) {
					stringemailBuilder.append(toEmail[i]);
				}else {
					stringemailBuilder.append(toEmail[i]+",");
				}
			}
			System.out.print(stringemailBuilder.toString());
			//tran huu dat danh sach cac email
			msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(stringemailBuilder.toString(), false));//gui nhieu email
			System.out.println("Message is ready");
			Transport.send(msg);

			System.out.println("EMail Sent Successfully!!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
			return false;
		}
	}
}
