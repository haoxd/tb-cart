package com.tb.cart.mysql.datasource.holder;


public class DynamicDataSourceHolder {
    
    //写库对应的数据源key
    public static final String MASTER = "master";

    //读库对应的数据源key 01
    private static final String SLAVE01 = "slave01";
    
    //读库对应的数据源key 01
    private static final String SLAVE02 = "slave02";
    
    //使用ThreadLocal记录当前线程的数据源key
    private static final ThreadLocal<String> holder = new ThreadLocal<String>();

    /**
     * 设置数据源key
     * @param key
     */
    public static void putDataSourceKey(String key) {
        holder.set(key);
    }

    /**
     * 获取数据源key
     * @return
     */
    public static String getDataSourceKey() {
        return holder.get();
    }
    
    /**
     * 标记写库
     */
    public static void markMaster(){
        putDataSourceKey(MASTER);
    }
    
    /**
     * 标记读库
     */
    public static void markSlave(){
        putDataSourceKey(SLAVE01);
    }
   

	public static boolean isMaster() {
		if(getDataSourceKey().equals(MASTER)){
			return true;
		}
		return false;
	}

}