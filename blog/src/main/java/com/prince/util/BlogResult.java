/**
 * @AUTHOR Prince
 * @TIME 2021/6/1 18:37
 */

package com.prince.util;

public class BlogResult {
    public int status;
    public String msg;
    public Object data;

    public BlogResult(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public BlogResult(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public BlogResult() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
