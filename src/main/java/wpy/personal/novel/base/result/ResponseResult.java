package wpy.personal.novel.base.result;

import lombok.Data;
import wpy.personal.novel.base.enums.ResponseCode;

import java.io.Serializable;

//处理结果
@Data
public class ResponseResult<T> implements Serializable {

	private int code;//200-处理成功，0-处理失败
	private String msg;
	private T data;

	private static final Integer SUCCESS_CODE = 200;
	private static final Integer ERROR_CODE = 0;
	private static final String SUCCESS_MSG="操作成功";
	private static final String ERROR_MSG="操作失败";

	/**
	 *  成功时候的调用
	 * */
	public static  <T> ResponseResult<T> success(){
		return new ResponseResult<T>();
	}

	public static  <T> ResponseResult<T> success(T data){
		return new ResponseResult<T>(data);
	}

	public static  <T> ResponseResult<T> success(int code,T data){
		return new ResponseResult<T>(code,data);
	}

	public static  <T> ResponseResult<T> success(int code,String msg,T data){
		return new ResponseResult<T>(code,msg,data);
	}

	public static  <T> ResponseResult<T> success(String msg,T data){
		return new ResponseResult<T>(SUCCESS_CODE,msg,data);
	}

	public static  <T> ResponseResult<T> success(ResponseCode responseCode,T data){
		return new ResponseResult<T>(responseCode.code,responseCode.msg,data);
	}

	/**
	 *  失败时候的调用
	 * */
	public static  <T> ResponseResult<T> error(){
		return new ResponseResult<T>(ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
	}

	public static  <T> ResponseResult<T> error(int code,String msg){
		return new ResponseResult<T>(code,msg);
	}

	public static  <T> ResponseResult<T> error(String msg){
		return new ResponseResult<T>(ERROR_CODE,msg);
	}

	public static  <T> ResponseResult<T> error(ResponseCode responeseCode){
		return new ResponseResult<T>(responeseCode);
	}


	/**
	 * 成功的构造函数
	 */
	private ResponseResult() {
		this.code = SUCCESS_CODE;
		this.msg = SUCCESS_MSG;
	}

	private ResponseResult(T data){
		this.code=SUCCESS_CODE;
		this.msg=SUCCESS_MSG;
		this.data = data;
	}

	public ResponseResult(int code, T data) {
		this.code=code;
		this.msg=SUCCESS_MSG;
		this.data=data;
	}

	public ResponseResult(int code, String msg, T data) {
		this.code=code;
		this.msg=msg;
		this.data=data;
	}

	/**
	 * 失败的构造函数
	 */

	private ResponseResult(String msg) {
		this.code = ERROR_CODE;
		this.msg = msg;
	}

	private ResponseResult(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private ResponseResult(ResponseCode responeseCode) {
		if(responeseCode != null) {
			this.code = responeseCode.getCode();
			this.msg = responeseCode.getMsg();
		}
	}
}
