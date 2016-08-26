/**
 * Copyright(c) Beijing Kungeek Science & Technology Ltd. 
 */
package cn.gb40;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author mmr  mmr@kungeek.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class entity {
	
	private String time;
	private String UV;
	private String PV;
	private String title;
	private String ck_module;
	private String pushdate;
	private int pvcount;
	private int uvcount;
	private String discoveryid;

	
	/**
	 * @return 返回 pvcount。
	 */
	public int getPvcount() {
		return pvcount;
	}
	/**
	 * @param pvcount 设置 pvcount。
	 */
	public void setPvcount(int pvcount) {
		this.pvcount = pvcount;
	}
	/**
	 * @return 返回 uvcount。
	 */
	public int getUvcount() {
		return uvcount;
	}
	/**
	 * @param uvcount 设置 uvcount。
	 */
	public void setUvcount(int uvcount) {
		this.uvcount = uvcount;
	}
	/**
	 * @return 返回 time。
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time 设置 time。
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return 返回 uV。
	 */
	public String getUV() {
		return UV;
	}
	/**
	 * @param uV 设置 uV。
	 */
	public void setUV(String uV) {
		UV = uV;
	}
	/**
	 * @return 返回 pV。
	 */
	public String getPV() {
		return PV;
	}
	/**
	 * @param pV 设置 pV。
	 */
	public void setPV(String pV) {
		PV = pV;
	}
	/**
	 * @return 返回 title。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title 设置 title。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return 返回 ck_module。
	 */
	public String getCk_module() {
		return ck_module;
	}
	/**
	 * @param ck_module 设置 ck_module。
	 */
	public void setCk_module(String ck_module) {
		this.ck_module = ck_module;
	}
	/**
	 * @return 返回 pushdate。
	 */
	public String getPushdate() {
		return pushdate;
	}
	/**
	 * @param pushdate 设置 pushdate。
	 */
	public void setPushdate(String pushdate) {
		this.pushdate = pushdate;
	}
	/**
	 * @return 返回 discoveryid。
	 */
	public String getDiscoveryid() {
		return discoveryid;
	}
	/**
	 * @param discoveryid 设置 discoveryid。
	 */
	public void setDiscoveryid(String discoveryid) {
		this.discoveryid = discoveryid;
	}
	
	

}
