package com.zhita.model.manage;

public class Feedback {
    private Integer id;

    private Integer userid;

    private String advice;

    private String url1;

    private String url2;

    private String url3;
    
    private String time;
    
    private String name;//用户姓名
    
    private String phone;//用户手机号
    
    private String solvestatus;//解决状态（1：已解决；2：未解决）
    
    private String replycontent;//回复内容
    
    private String dmy;//年月日
    
    private String hms;//时分秒

    public Feedback(Integer id, Integer userid, String advice, String url1, String url2, String url3,String time,String dmy,String hms) {
        this.id = id;
        this.userid = userid;
        this.advice = advice;
        this.url1 = url1;
        this.url2 = url2;
        this.url3 = url3;
        this.time = time;
        this.dmy = dmy;
        this.hms = hms;
    }

    public Feedback() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice == null ? null : advice.trim();
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1 == null ? null : url1.trim();
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2 == null ? null : url2.trim();
    }

    public String getUrl3() {
        return url3;
    }

    public void setUrl3(String url3) {
        this.url3 = url3 == null ? null : url3.trim();
    }
    
    
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSolvestatus() {
		return solvestatus;
	}

	public void setSolvestatus(String solvestatus) {
		this.solvestatus = solvestatus;
	}

	public String getReplycontent() {
		return replycontent;
	}

	public void setReplycontent(String replycontent) {
		this.replycontent = replycontent;
	}
	
	public String getDmy() {
		return dmy;
	}

	public void setDmy(String dmy) {
		this.dmy = dmy;
	}
	
	public String getHms() {
		return hms;
	}

	public void setHms(String hms) {
		this.hms = hms;
	}
    
}