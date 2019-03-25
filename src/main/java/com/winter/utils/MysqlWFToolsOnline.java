package com.winter.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * mysql上流程模板迁移工具：
 * 功能：{
 * 1.可从源数据为库中导出指定sql的流程模板信息
 * 2.可将真正执行的查询sql输出到指定文件
 * 3.可将导出的insert语句输出的文件
 * 4.可在目标数据库执行insert语句
 * }
 * 流程模板查询sql定义如下
 * {
 * --下面第一行定义sql中的用到的变量${table.field}用=号分隔;按sql的顺序保证变量取值;注：每条sql一行
 * --${wfpackage.innerid}=${wfpackage.packageid}=${wfprodefinition.innerid}=${
 * wfprodefinition.prodefinitionid}
 * select * from wfdirectory aa where
 * aa.directoryid='6a79d466ccb540faa02552a3ce4f686d';
 * select * from wfpackage a where a.innerid in (
 * '2fd281161e2f400a9f2487b50733633f','1eb6fc71558149dfaadfd5f1b4c7a65c','9d895584c7654ef595f57fb07dde56c2','477fec45ecd54452979039c9264be0
 * 0
 * f
 * '
 * )
 * ;
 * select * from wfprodefinition b where b.packageinnerid =
 * '${wfpackage.innerid}';
 * select * from wfactivity c where c.prodefinitioninnerid =
 * '${wfprodefinition.innerid}';
 * select * from wftransition d where d.prodefinitioninnerid =
 * '${wfprodefinition.innerid}';
 * select * from wfprodefinitionxml e where e.prodefinitioninnerid =
 * '${wfprodefinition.innerid}';
 * select * from wfdefinitionrelevantdata f where f.packageinnerid =
 * '${wfpackage.innerid}';
 * select * from wfactivityparticipant h where h.prodefinitioninnerid =
 * '${wfprodefinition.innerid}';
 * select * from wfactivityrelevantdata i where i.prodefinitioninnerid =
 * '${wfprodefinition.innerid}';
 * select * from wfappendproperties j where j.prodefinitioninnerid =
 * '${wfprodefinition.innerid}';
 * --delete from WFGRAPHPACKAGE@SERVERORCL where PACKAGEID = pkg.packageid;
 * --select * from WFGRAPHPACKAGE k where k.packageid = '${wfpackage.packageid}';
 * --delete from WFPROCESSGRAPH@SERVERORCL where innerid = pdf.prodefinitionid;
 * --select * from WFPROCESSGRAPH l where l.innerid =
 * '${wfprodefinition.prodefinitionid}';
 * select * from WFEVENTLISTENER m where m.prodefinitioninnerid =
 * '${wfprodefinition.innerid}';
 * select * from WFROUTECONDITION n where n.wfprodefid =
 * '${wfprodefinition.innerid}';
 * select * from WFROLLBACKACTSCOPE o where o.wfprodefid =
 * '${wfprodefinition.innerid}';
 * 
 * }
 * 
 * @author zhangxm
 * 
 */
public class MysqlWFToolsOnline {

	/**
	 * 需要导出的数据库配置
	 */
	private String[] sourceDb = {
			"com.mysql.cj.jdbc.Driver",
			//"jdbc:mysql://localhost:3306/adp_test_from?useUnicode=true&amp;characterEncoding=utf8&amp;noAccessToProcedureBodies=true",
			"jdbc:mysql://127.0.0.1:3306/adp_test_from?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=UTF-8&useSSL=true",
			"root", "success" };
	/**
	 * 导入目标数据库配置
	 */
	private String[] targetDb = {
			//"com.mysql.jdbc.Driver",
			"com.mysql.cj.jdbc.Driver",
			"jdbc:mysql://127.0.0.1:3306/db_test_to?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=UTF-8&useSSL=true",
			"root", "success" };
	/**
	 * 编码格式
	 */
	
	private String charset = "UTF-8";
	public List<String> getExecSqlList() {
		return execSqlList;
	}

	public void setExecSqlList(List<String> execSqlList) {
		this.execSqlList = execSqlList;
	}

	public String[] getSourceDb() {
		return sourceDb;
	}

	public void setSourceDb(String[] sourceDb) {
		this.sourceDb = sourceDb;
	}

	public String[] getTargetDb() {
		return targetDb;
	}

	public void setTargetDb(String[] targetDb) {
		this.targetDb = targetDb;
	}

	public List<String> getOutSqlList() {
		return outSqlList;
	}

	public void setOutSqlList(List<String> outSqlList) {
		this.outSqlList = outSqlList;
	}

	/**
	 * 执行查询－sql的集合
	 */
	private List<String> execSqlList = new ArrayList<String>();
	/**
	 * 输出的insert－sql的集合
	 */
	private List<String> outSqlList = new ArrayList<String>();
	
	/**
	 * 输出的insert－sql 替换Id的集合
	 */
	private List<String> convertSqlList = new ArrayList<String>();
	
	public List<String> getConvertSqlList() {
		return convertSqlList;
	}

	public void setConvertSqlList(List<String> convertSqlList) {
		this.convertSqlList = convertSqlList;
	}

	/**
	 * blob对象处理
	 */
	private List<TableJob> jobList = new ArrayList<TableJob>();
	/**
	 * 变量
	 */
	private Map<String, List<String>> varMap = new HashMap<String, List<String>>();
	/**
	 * 需要先删除的数据
	 */
	private List<String> delTabName = new ArrayList<String>();
	/**
	 * 是否导成新数据
	 * true:所有主要innerid将会被替换成新id；
	 */
	private boolean exportNewData = true;
	/**
	 * 替换主键ID
	 */
	private Map<String, String> idMap = new HashMap<String, String>();
	/**
	 * 主键过滤
	 */
	private String[] idFilter = { "innerid", "directoryid","packageid","prodefinitionid" };

	/*static {
		delTabName.add("wfgraphpackage");
		delTabName.add("wfprocessgraph");
		idMap.put("2米8米星流程模板", "GF6流程模板");
		idMap.put("GF1_", "GF6_");
	}*/
	public MysqlWFToolsOnline()
	{
		delTabName.add("wfgraphpackage");
		delTabName.add("wfprocessgraph");
		idMap.put("2米8米星流程模板", "GF6流程模板");
		idMap.put("GF1_", "GF6_");
	}

	/**
	 * 
	 * @param sqlList
	 * @return
	 */
	public List<String> exportInserSql(List<String> sqlList) {
		try {
			for (String sql : sqlList) {
				getVariable(sql);
				List<String> conList = convertSql(sql);
				for (String c : conList) {
					/*if(judgeSqlExecute(c))
					{*/
						execSqlList.add(c);
						executeQuerySql(c);
					/*}*/
				
				}
			}
			convertSqlListByOutSqlList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 判断sql是否执行
	 * @param sqlList
	 * @return
	 */
	public static boolean judgeSqlExecute(String sqlString)
	{
		// 先获取定义的变量
		if (sqlString.startsWith("--$")) {
			return false;
		}
		if (sqlString.length() == 0 || sqlString.startsWith("--")
				|| sqlString.startsWith("#")) {
			return false;
		}
		return true;
	}

	/**
	 * 再目标数据库中执行insert语句
	 * 
	 * @param sqlList
	 */
	public void execInsertSql(List<String> sqlList) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		try {
			conn = dbConnect(targetDb);
			st = conn.createStatement();
			for (String s : sqlList) {
				System.out.println(s);
				//st.addBatch(s);
				st.execute(s);
			}
			//st.executeBatch();
			// 更新blob字段
			for (TableJob tj : jobList) {
				StringBuilder sb = new StringBuilder();
				sb.append("update ").append(tj.tableName).append(" set ");
				// 要修改的字段
				int i = 0;
				for (String colName : tj.blobs.keySet()) {
					i++;
					sb.append(colName).append(" = ? ");
					if (i < tj.blobs.size() - 1) {
						sb.append(",");
					}
				}
				sb.append(" where  ");
				i = 0;
				for (String colName : tj.keys.keySet()) {
					i++;
					sb.append(colName).append(" = ? ");
					if (i < tj.keys.size() - 1) {
						sb.append("and");
					}
				}
				System.out.println("开始更新部分表中的blob字段内容:" + sb.toString());
				String newSb = sb.toString();
				if (exportNewData) {
					for (String oldId : idMap.keySet()) {
						String newId = idMap.get(oldId);
						newSb.replaceAll(oldId, newId);
						System.out.println("(替换新ID)开始更新部分表中的blob字段内容:" + newSb);
					}
				}
				// 处理变更sql值
				ps = conn.prepareStatement(newSb);
				i = 0;
				for (String colName : tj.blobs.keySet()) {
					i++;
					ps.setBinaryStream(i, tj.blobs.get(colName));
				}
				for (String colName : tj.keys.keySet()) {
					i++;
					ps.setString(i, tj.keys.get(colName));
				}
				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	/**
	 * 从sql中获取表名
	 * 
	 * @param querySql
	 * @return
	 */
	private String getTableName(String sql) {
		int i = sql.indexOf("from ", 1) + 5;
		int k = sql.indexOf(" ", i);
		return sql.substring(i, k);
	}

	/**
	 * 从sql中获取变量
	 * 
	 * @param querySql
	 * @return
	 */
	private void getVariable(String sql) {
		sql = sql.replaceAll(";", "");
		String[] args = sql.split("=");
		for (String v : args) {
			int i = v.indexOf("$");
			if (i > -1) {
				String var = v.substring(i + 2, v.indexOf("}")).trim()
						.toLowerCase();
				List<String> varList = varMap.get(var);
				if (null == varList) {
					varMap.put(var, new ArrayList<String>());
				}
			}
		}
	}

	/**
	 * 替换sql中的变量
	 * 变量值为多个时，将分成多条sql语句
	 * 
	 * @param sql
	 * @return
	 */
	private List<String> convertSql(String sql) {
		List<String> rsList = new ArrayList<String>();
		Iterator<String> iter = varMap.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			if (sql.contains(key)) {
				List<String> list = varMap.get(key);
				for (String s : list) {
					String tempSql = sql.replaceAll("\\$\\{" + key + "\\}", s);
					rsList.add(tempSql);
				}
			}
		}
		if (rsList.size() == 0) {
			rsList.add(sql);
		}
		return rsList;
	}

	/**
	 * 拼装查询语句
	 * 
	 * @return 返回select集合
	 */
	private List<String> readInputFile(String file) throws Exception {
		List<String> listSQL = new ArrayList<String>();
		BufferedReader br = null;
		InputStreamReader fr = null;
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			fr = new InputStreamReader(is, charset);
			br = new BufferedReader(fr);
			String rec = null;// 一行
			while ((rec = br.readLine()) != null) {
				// 先获取定义的变量
				if (rec.startsWith("--$")) {
					getVariable(rec);
					continue;
				}
				if (rec.length() == 0 || rec.startsWith("--")
						|| rec.startsWith("#")) {
					continue;
				}
				// 返回小写
				String tempRec = rec.toLowerCase();
				// 条件区分大小写
				int i = tempRec.indexOf("where");
				if (i > -1) {
					tempRec = tempRec.substring(0, i)
							+ rec.substring(i, rec.length());
				}
				// 获取所有查询语句
				listSQL.add(tempRec.trim());
			}

		} finally {
			if (br != null) {
				br.close();
			}
			if (fr != null) {
				fr.close();
			}
			if (is != null) {
				is.close();
			}
		}
		return listSQL;
	}
	
	/**
	 * 拼装查询语句
	 * 
	 * @return 返回select集合
	 */
	public List<String> readList(List<String> sqlList) throws Exception {
		List<String> listSQL = new ArrayList<String>();
		try {
			for(String rec:sqlList)
			{
				// 先获取定义的变量
				if (rec.startsWith("--$")) {
					getVariable(rec);
					continue;
				}
				if (rec.length() == 0 || rec.startsWith("--")
						|| rec.startsWith("#")) {
					continue;
				}
				// 返回小写
				String tempRec = rec.toLowerCase();
				// 条件区分大小写
				int i = tempRec.indexOf("where");
				if (i > -1) {
					tempRec = tempRec.substring(0, i)
							+ rec.substring(i, rec.length());
				}
				// 获取所有查询语句
				listSQL.add(tempRec.trim());
			}

		} finally {
			
		}
		return listSQL;
	}

	/**
	 * 连接数据库
	 * 
	 * @param driver
	 * @param url
	 * @param UserName
	 * @param Password
	 */
	private Connection dbConnect(String[] dbInfo) {
		try {
			Class.forName(dbInfo[0]).newInstance();
			return DriverManager.getConnection(dbInfo[1], dbInfo[2], dbInfo[3]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 执行查询sql
	 * 
	 * @param sql
	 */
	private void executeQuerySql(String sql) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// System.out.println(sql);
			String tableName = getTableName(sql);
			if (delTabName.contains(tableName) && !exportNewData) {
				String delSql = sql;
				outSqlList.add(0, delSql.replace("select *", "delete"));
			}
			conn = dbConnect(sourceDb);
			ps = conn.prepareStatement(sql);
			TableJob tableJob = getPrimaryKeys(conn, tableName);
			rs = ps.executeQuery();
			getColumnNameAndColumeValue(tableJob, rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	/**
	 * 获取表主键
	 * 
	 * @param conn
	 * @param talbeName
	 */
	private TableJob getPrimaryKeys(Connection conn, String talbeName) {
		ResultSet rs = null;
		try {
			TableJob tj = new TableJob(talbeName);
			DatabaseMetaData dmd = conn.getMetaData();
			rs = dmd.getPrimaryKeys(conn.getCatalog(), null, talbeName);
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				tj.keys.put(columnName.toLowerCase(), null);
			}
			return tj;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		return null;
	}

	/**
	 * 找了段代码用用
	 * 中间加了点变量的处理
	 * 
	 * @param tableName
	 * @param rs
	 * @throws SQLException
	 */
	private void getColumnNameAndColumeValue(TableJob tableJob,
			ResultSet rs) throws Exception {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		while (rs.next()) {
			StringBuffer ColumnName = new StringBuffer();
			StringBuffer ColumnValue = new StringBuffer();
			for (int i = 1; i <= columnCount; i++) {
				int columnType = rsmd.getColumnType(i);
				String name = rsmd.getColumnName(i).toLowerCase();
				String value = rs.getString(i);
				// 特殊值转换特殊符号
				if (null != value) {
					value = value.replace("\'", "\\\"");
					value = value.replace("\"", "\\\"");
				}
				if (i == columnCount) {
					ColumnName.append(name);
					if (Types.CHAR == columnType || Types.VARCHAR == columnType
							|| Types.LONGVARCHAR == columnType) {
						if (value == null) {
							ColumnValue.append("null");
						} else {
							ColumnValue.append("'").append(value).append("'");
						}
					} else if (Types.SMALLINT == columnType
							|| Types.INTEGER == columnType
							|| Types.BIGINT == columnType
							|| Types.FLOAT == columnType
							|| Types.DOUBLE == columnType
							|| Types.NUMERIC == columnType
							|| Types.DECIMAL == columnType) {
						if (value == null) {
							ColumnValue.append("null");
						} else {
							ColumnValue.append(value);
						}
					} else if (Types.DATE == columnType
							|| Types.TIME == columnType
							|| Types.TIMESTAMP == columnType) {
						if (value == null) {
							ColumnValue.append("null");
						} else {
							ColumnValue.append("timestamp'").append(value)
									.append("'");
						}
					} else if (Types.LONGVARBINARY == columnType
							|| Types.BLOB == columnType) {
						ColumnValue.append("null");
						// blob字段处理
						InputStream is = rs.getBinaryStream(name);
						if (null != is) {
							tableJob.blobs.put(name, is);
						}
					} else {
						if (value == null) {
							ColumnValue.append("null");
						} else {
							ColumnValue.append(value);
						}
					}
				} else {
					ColumnName.append(name + ",");
					if (Types.CHAR == columnType || Types.VARCHAR == columnType
							|| Types.LONGVARCHAR == columnType) {
						if (value == null) {
							ColumnValue.append("null,");
						} else {
							ColumnValue.append("'").append(value).append("',");
						}
					} else if (Types.SMALLINT == columnType
							|| Types.INTEGER == columnType
							|| Types.BIGINT == columnType
							|| Types.FLOAT == columnType
							|| Types.DOUBLE == columnType
							|| Types.NUMERIC == columnType
							|| Types.DECIMAL == columnType) {
						if (value == null) {
							ColumnValue.append("null,");
						} else {
							ColumnValue.append(value).append(",");
						}
					} else if (Types.DATE == columnType
							|| Types.TIME == columnType
							|| Types.TIMESTAMP == columnType) {
						if (value == null) {
							ColumnValue.append("null,");
						} else {
							ColumnValue.append("timestamp'").append(value)
									.append("',");
						}
					} else if (Types.LONGVARBINARY == columnType
							|| Types.BLOB == columnType) {
						ColumnValue.append("null,");
						// blob字段处理
						InputStream is = rs.getBinaryStream(name);
						if (null != is) {
							ObjectInputStream ois = new ObjectInputStream(is);
							tableJob.blobs.put(name, ois);
						}
					} else {
						if (value == null) {
							ColumnValue.append("null,");
						} else {
							ColumnValue.append(value).append(",");
						}
					}
				}
				// 保存变量值
				String tkey = tableJob.tableName + "." + name;
				Iterator<String> iter = varMap.keySet().iterator();
				while (iter.hasNext()) {
					String key = iter.next();
					if (key.equals(tkey)) {
						varMap.get(key).add(value);
					}
				}
				// 记录表主键和blob
				iter = tableJob.keys.keySet().iterator();
				while (iter.hasNext()) {
					String key = iter.next();
					if (key.equals(name)) {
						tableJob.keys.put(key, value);
					}
				}
				// 替换ID
				if (exportNewData) {
					mapIds(name, value);
				}

			}
			// 需要更新的表的blob
			if (tableJob.blobs.size() > 0) {
				jobList.add(tableJob);
			}
			// 组织insert语句
			String insertSql = concatInsertSQL(tableJob.tableName, ColumnName,
					ColumnValue);
			outSqlList.add(insertSql);
		}
	}

	/**
	 * 转换主键ID
	 * 
	 * @param column
	 * @param value
	 */
	private void mapIds(String column, String value) {
		// 组织需要替换的ID
		if (Arrays.asList(idFilter).contains(column)) {
			idMap.put(value, getUID());
		}
	}

	public synchronized String getUID() {
		String uuid = java.util.UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");
	}

	/**
	 * 把sql输出的文件
	 */
	private void createFile(List<String> sqlList, String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("创建文件名失败！！");
				e.printStackTrace();
			}
		}
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			if (sqlList.size() > 0) {
				for (Integer i = 0; i < sqlList.size(); i++) {
					System.out.print("write");
					System.out.println(i);
					
					System.out.println(sqlList.get(i));
					bw.append(i.toString());
					bw.append(sqlList.get(i));
					bw.append("\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 拼装insertsql 放到全局list里面
	 * 
	 * @param ColumnName
	 * @param ColumnValue
	 */
	private String concatInsertSQL(String TableName,
			StringBuffer ColumnName, StringBuffer ColumnValue) {
		StringBuffer insertSQL = new StringBuffer();
		insertSQL.append("insert into ").append(TableName).append("(")
				.append(ColumnName.toString()).append(")").append(" values")
				.append("(").append(ColumnValue.toString()).append(");");
		return insertSQL.toString();
	}
	
	/**
	 * 拼装insertsql 放到全局list里面
	 * 
	 * @param ColumnName
	 * @param ColumnValue
	 */
	private void convertSqlListByOutSqlList() {
		for (String iSql : outSqlList) {
			String nSql = iSql;
			for (String oldId : idMap.keySet()) {
				String newId = idMap.get(oldId);
				if (nSql.contains(oldId)) {
					nSql = nSql.replaceAll(oldId, newId);
				}
			}
			convertSqlList.add(nSql);
		}
	}

	public static void main(String[] args) throws Exception {
		MysqlWFToolsOnline mysqlTools = new MysqlWFToolsOnline();
		String path = "D:/DISK/SQL/";
		//exportNewData = false; 
		System.out.println(System.currentTimeMillis() + ":"
				+ MysqlWFToolsOnline.class.getName() + ":开始读取查询语句配置文件");
		List<String> list = mysqlTools.readInputFile(path + "input.sql");
		System.out.println(System.currentTimeMillis() + ":"
				+ MysqlWFToolsOnline.class.getName() + ":开始执行查询语句");
		mysqlTools.exportInserSql(list);
		System.out.println(System.currentTimeMillis() + ":"
				+ MysqlWFToolsOnline.class.getName() + ":执行查询语句完成");
		mysqlTools.createFile(mysqlTools.execSqlList, path + "output_s.sql");
		System.out.println(System.currentTimeMillis() + ":"
				+ MysqlWFToolsOnline.class.getName() + ":输出查询语句完成");
		mysqlTools.createFile(mysqlTools.outSqlList, path + "output_i.sql");
		int i = 0;
		for(String outSqlOne :mysqlTools.outSqlList)
		{
			
			System.out.println(i++);
			System.out.println(outSqlOne);
		}
		System.out.println(System.currentTimeMillis() + ":"
				+ MysqlWFToolsOnline.class.getName() + ":输出insert语句完成");
		if (mysqlTools.exportNewData) {
			
			
			mysqlTools.createFile(mysqlTools.convertSqlList, path + "output_new_i.sql");
			System.out.println(System.currentTimeMillis() + ":"
					+ MysqlWFToolsOnline.class.getName() + ":输出insert语句完成");
			mysqlTools.outSqlList.clear();
			mysqlTools.outSqlList.addAll(mysqlTools.convertSqlList);
		}
		System.out.println(System.currentTimeMillis() + ":"
				+ MysqlWFToolsOnline.class.getName() + ":开始在目标库执行insert语句");
		mysqlTools.execInsertSql(mysqlTools.outSqlList);
		System.out.println(System.currentTimeMillis() + ":"
				+ MysqlWFToolsOnline.class.getName() + ":在目标库执行insert语句完成");
	}
}

/*class TableJob {
	public String tableName;
	public Map<String, String> keys = new HashMap<String, String>();
	public Map<String, InputStream> blobs = new HashMap<String, InputStream>();

	public TableJob(String tableName) {
		this.tableName = tableName;
	}

}*/