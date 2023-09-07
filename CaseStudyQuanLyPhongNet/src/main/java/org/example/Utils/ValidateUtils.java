package org.example.Utils;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ValidateUtils {
    public static final String REGEX_USERNAME = "^[a-zA-Z][a-zA-Z0-9_-]{3,16}$";
    public static final String REGEX_PRODUCTNAME = "^(?!.*\\d)[a-zA-Z]{5,20}$";
    public static final String REGEX_ACTIONCONTINUE= "^[YN]$";
    public static final String REGEX_BALANCE = "^\\d*0{3}$";
    public static final String REGEX_PRODUCTPRICE = "^(10[0-9]{3}|100000)0{3}$";
    public static final String REGEX_QUANTITYPRODUCT = "^(?:[1-9]|1\\d|20)$";
    public static final String REGEX_IDPRODUCT = "^[1-9]$";
    public static final String REGEX_FULLNAME = "^(?! )[A-Za-z\\p{L}\\s]{1,50}$";
    public static final String REGEX_DOB = "^(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[0-2])-(19[5-9][2-9]|20[01][0-9])$";
    public static final String REGEX_DATE = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(2022|20[23]\\\\d)$\n";
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{5,20}$";
    public static final String REGEX_ORDERID = "^[0-9]{1,5}$";
    public static final String REGEX_AGE = "^(1[4-9]|[2-6][0-9]|70)$";
    public static final String REGEX_COMPUTER = "^MAY\\d{2}$";
    public static final String REGEX_PERSONALID = "^0\\d{11}$";
    public static final String FIELD_COMPUTER = "tên máy";
    public static final String FIELD_IDPRODUCT = "id sản phẩm";
    public static final String FIELD_DEPOSIT = "số tiền";
    public static final String FIELD_BALANCE = "số dư";
    public static final String FIELD_PRODUCTNAME = "tên sản phẩm";
    public static final String FIELD_PRODUCTPRICE = "giá sản phẩm";
    public static final String FIELD_FULLNAME = "họ và tên";
    public static final String FIELD_PRODUCTDES = "mô tả sản phẩm";
    public static final String FIELD_USER = "tên đăng nhập";
    public static final String FIELD_ACTIONCONTINUE = "Y/N";
    public static final String FIELD_AGE = "tuổi";
    public static final String FIELD_DOB = "ngày tháng năm sinh(dd-MM-yyyy)";
    public static final String FIELD_DATE = "ngày tháng năm (dd-MM-yyyy)";
    public static final String FIELD_PERSONALID = "mã căn cước công dân";
    public static final String FIELD_ORDERID = "id của order";
    public static final String FIELD_PRODUCTQUANTITY = "số lượng sản phẩm";
    public static final String FIELD_NEW_COMPUTER = "tên máy mới";
    public static final String FIELD_PASSWORD = "mật khẩu";
    public static final String FIELD_PASSWORDNEW = "mật khẩu mới";
    public static final String FIELD_COMPUTER_MESSAGE = "Tên máy phải bắt đầu với từ MAY,2 ký tự tiếp theo là số ";
    public static final String FIELD_DOB_MESSAGE = "Ngày tháng phải theo dạng dd-MM-yyyy";
    public static final String FIELD_QUANTITYP_MESSAGE = "Số lượng sản phẩm nằm trong khoảng 1-20";
    public static final String FIELD_IDPRODUCT_MESSAGE = "ID của sản phẩm phải là một số từ 1-9";
    public static final String FIELD_PRODUCTPRICE_MESSAGE = "Giá sản phẩm phải từ 10000-100000 đồng";
    public static final String FIELD_PRODUCTNAME_MESSAGE = "Tên sản phẩm phải có độ dài từ 5-20 ký tự và không được chứa ký tự số";
    public static final String FIELD_PRODUCTDES_MESSAGE = "Mô tả sản phẩm phải có độ dài từ 5-20 ký tự và không được chứa ký tự số";
    public static final String FIELD_FULLNAME_MESSAGE = "Tên bắt đầu bằng kí tự, có từ 1 - 50 kí tự";
    public static final String FIELD_USER_MESSAGE = "Có độ dài từ 3-16 ký tự";
    public static final String FIELD_PASSWORD_MESSAGE = "Mật khẩu phải có độ dài từ 5-20 ký tự,chỉ chứa các ký tự alphabet, và ký tự số ";
    public static final String FIELD_ACTIONCONTINUE_MESSAGE = "Chỉ được nhập 1 trong 2 ký tự Y/N ";
    public static final String FIELD_IDORDER_MESSAGE = "ID của order phải có 1-5 ký tự từ 0-9";
    public static final String FIELD_BALANCE_MESSAGE = "Sô tiền nạp phải lớn hơn 1000 đồng, và các ký tự là con số ";
    public static final String FIELD_AGE_MESSAGE = "Tuổi phải là ký tự số nằm trong khoảng từ 14-70 ";
    public static final String FIELD_PID_MESSAGE = "Căn cước công dân phải có 12 ký tự số và bắt đầu bằng số 0";

    //public static  final String REGEX_USERNAME=" ";
//public static final
    public static boolean isValid(String pattern, String strInput) {
        return Pattern.matches(pattern, strInput);
    }

    public static int getIntOfWithBounds(int min, int max) {
        Scanner scanner =new Scanner(System.in);
        try {
            int enter = Integer.parseInt(scanner.nextLine());
            if (enter > max || enter < min) {
//                throw new Exception("Number invalid!");
                throw new Exception("Không hợp lệ. Nhập lại giá trị trong khoảng " + min + " - " + max + "\n");
            }
            return enter;
        } catch (Exception e) {
            System.out.printf("Không hợp lệ. Nhập lại giá trị trong khoảng " + min + " - " + max + "\n");
//            System.out.println("Number invalid");
            return getIntOfWithBounds(min, max);
        }
    }
}
