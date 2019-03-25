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
 * mysql上流程模板迁移工具： 功能：{ 1.可从源数据为库中导出指定sql的流程模板信息 2.可将真正执行的查询sql输出到指定文件
 * 3.可将导出的insert语句输出的文件 4.可在目标数据库执行insert语句 } 流程模板查询sql定义如下 {
 * --下面第一行定义sql中的用到的变量${table.field}用=号分隔;按sql的顺序保证变量取值;注：每条sql一行
 * --${wfpackage.innerid
 * }=${wfpackage.packageid}=${wfprodefinition.innerid}=${wfprodefinition
 * .prodefinitionid} select * from wfdirectory aa where
 * aa.directoryid='6a79d466ccb540faa02552a3ce4f686d'; select * from wfpackage a
 * where a.innerid in (
 * '2fd281161e2f400a9f2487b50733633f','1eb6fc71558149dfaadfd5f1b4c7a65c','9d895584c7654ef595f57fb07dde56c2','477fec45ecd54452979039c9264
 * b e 0 0 f ' ) ; select * from wfprodefinition b where b.packageinnerid =
 * '${wfpackage.innerid}'; select * from wfactivity c where
 * c.prodefinitioninnerid = '${wfprodefinition.innerid}'; select * from
 * wftransition d where d.prodefinitioninnerid = '${wfprodefinition.innerid}';
 * select * from wfprodefinitionxml e where e.prodefinitioninnerid =
 * '${wfprodefinition.innerid}'; select * from wfdefinitionrelevantdata f where
 * f.packageinnerid = '${wfpackage.innerid}'; select * from
 * wfactivityparticipant h where h.prodefinitioninnerid =
 * '${wfprodefinition.innerid}'; select * from wfactivityrelevantdata i where
 * i.prodefinitioninnerid = '${wfprodefinition.innerid}'; select * from
 * wfappendproperties j where j.prodefinitioninnerid =
 * '${wfprodefinition.innerid}'; --delete from WFGRAPHPACKAGE@SERVERORCL where
 * PACKAGEID = pkg.packageid; select * from WFGRAPHPACKAGE k where k.packageid =
 * '${wfpackage.packageid}'; --delete from WFPROCESSGRAPH@SERVERORCL where
 * innerid = pdf.prodefinitionid; select * from WFPROCESSGRAPH l where l.innerid
 * = '${wfprodefinition.prodefinitionid}'; select * from WFEVENTLISTENER m where
 * m.prodefinitioninnerid = '${wfprodefinition.innerid}'; select * from
 * WFROUTECONDITION n where n.wfprodefid = '${wfprodefinition.innerid}'; select
 * * from WFROLLBACKACTSCOPE o where o.wfprodefid =
 * '${wfprodefinition.innerid}';
 * 
 * }
 * 
 * @author zhangxm
 * 
 */
public class MysqlWFTools {

	/**
	 * 需要导出的数据库配置
	 */
	private static String[] sourceDb = {
			"com.mysql.jdbc.Driver",
			"jdbc:mysql://10.0.6.27:3306/oms_adp?useUnicode=true&amp;characterEncoding=utf8&amp;noAccessToProcedureBodies=true",
			"oms_adp", "oms_adp" };
	/**
	 * 导入目标数据库配置
	 */

	/*
	 * private static String[] targetDb = { "com.mysql.jdbc.Driver",
	 * "jdbc:mysql://172.15.100.151:3306/adp?useUnicode=true&amp;characterEncoding=utf8&amp;noAccessToProcedureBodies=true"
	 * , "adp", "adp" };
	 */

	private static String[] targetDb = {
			"com.mysql.jdbc.Driver",
			"jdbc:mysql://localhost:3306/adp?useUnicode=true&amp;characterEncoding=utf8&amp;noAccessToProcedureBodies=true",
			"root", "success" };
	/**
	 * 编码格式
	 */
	private static String charset = "UTF-8";
	/**
	 * 执行查询－sql的集合
	 */
	private static List<String> execSqlList = new ArrayList<String>();
	/**
	 * 输出的insert－sql的集合
	 */
	private static List<String> outSqlList = new ArrayList<String>();
	/**
	 * blob对象处理
	 */
	private static List<TableJob> jobList = new ArrayList<TableJob>();
	/**
	 * 变量
	 */
	private static Map<String, List<String>> varMap = new HashMap<String, List<String>>();
	/**
	 * 需要先删除的数据
	 */
	private static List<String> delTabName = new ArrayList<String>();

	/**
	 * $ 主键list 注：都是小写
	 */
	private static List<String> keyList = new ArrayList<String>();
	/**
	 * $ 非主键list 注：都是小写
	 */
	private static List<String> noKeyList = Arrays
			.asList("prodefinitioninnerid,activityid,transitionid,participantid,participanttype,participantid2,name"
					.split(","));
	
	private static boolean copyOrNot = false;
	/**
	 * $ 替换的ID Map
	 */
	private static Map<String, String> idReplaceMap = new HashMap();
	static {
		delTabName.add("wfgraphpackage");
		delTabName.add("wfprocessgraph");
		keyList.add("innerId".toLowerCase());
		idReplaceMap.put("81fcc3abeaf74924b4c8d9a80627040d", "8e4a5d790f0f4a88aa5b4cb1133e51a5");
	}

	/**
	 * 
	 * @param sqlList
	 * @return
	 */
	public static List<String> exportInserSql(List<String> sqlList) {
		try {
			for (String sql : sqlList) {
				getVariable(sql);
				List<String> conList = convertSql(sql);
				for (String c : conList) {
					execSqlList.add(c);
					executeQuerySql(c);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 再目标数据库中执行insert语句
	 * 
	 * @param sqlList
	 */
	public static void execInsertSql(List<String> sqlList) {
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
				// 处理变更sql值
				ps = conn.prepareStatement(sb.toString());
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
	private static String getTableName(String sql) {
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
	private static void getVariable(String sql) {
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
	 * 替换sql中的变量 变量值为多个时，将分成多条sql语句
	 * 
	 * @param sql
	 * @return
	 */
	private static List<String> convertSql(String sql) {
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
	private static List<String> readInputFile(String file) throws Exception {
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
	 * 连接数据库
	 * 
	 * @param driver
	 * @param url
	 * @param UserName
	 * @param Password
	 */
	private static Connection dbConnect(String[] dbInfo) {
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
	private static void executeQuerySql(String sql) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			System.out.println(sql);
			String tableName = getTableName(sql);
			if (delTabName.contains(tableName)) {
				String delSql = sql;
				//outSqlList.add(delSql.replace("select *", "delete"));
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
	private static TableJob getPrimaryKeys(Connection conn, String talbeName) {
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
	 * 找了段代码用用 中间加了点变量的处理
	 * 
	 * @param tableName
	 * @param rs
	 * @throws SQLException
	 */
	private static void getColumnNameAndColumeValue(TableJob tableJob,
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

				// $构造Map
				constructMapByNameValue(name, value, tableJob);
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

			}
			// 需要更新的表的blob
			if (tableJob.blobs.size() > 0) {
				jobList.add(tableJob);
			}

			// $替换Map
			// constructMapAndReplaceValue(ColumnName, ColumnValue);

			// 组织insert语句
			String insertSql = concatInsertSQL(tableJob.tableName, ColumnName,
					ColumnValue);
			outSqlList.add(insertSql);
		}
	}

	// $构造替换map并且返回替换后的value
	private static void constructMapByNameValue(String columnName,
			String columnValue, TableJob tableJob) {
		for (String keyStringFor : tableJob.keys.keySet()) {
			if (!keyList.contains(keyStringFor)
					&& !noKeyList.contains(keyStringFor)) {
				keyList.add(keyStringFor);
			}
		}
		for (String keyString : keyList) {
			if (keyString.equals(columnName)) {
				if (!idReplaceMap.keySet().contains(columnValue)) {
					String replaceValue = java.util.UUID.randomUUID()
							.toString().replace("-", "");
					idReplaceMap.put(columnValue, replaceValue);
				}
				break;
			}

		}
	}

	private static void constructMapByNameValue(String columnName,
			String columnValue) {
		for (String keyString : keyList) {
			if (keyString.equals(columnName)) {
				if (!idReplaceMap.keySet().contains(columnValue)) {
					String replaceValue = java.util.UUID.randomUUID()
							.toString().replace("-", "");
					idReplaceMap.put(columnValue, replaceValue);
				}
				break;
			}

		}
	}

	// $构造替换map并且返回替换后的value
	private static List<String> replaceIdByMap(List<String> sqlList) {
		int i = 0;
		List<String> sqlListTemp = new ArrayList();
		for (String sqlString : sqlList) {
			sqlListTemp.add(sqlString);
			for (String keyString : idReplaceMap.keySet()) {
				if (!sqlString.contains(keyString))
					continue;
				sqlString = sqlString.replaceAll(keyString,
						idReplaceMap.get(keyString));
				sqlListTemp.set(i, sqlString);

			}
			i++;
		}

		return sqlListTemp;

	}
	// $构造替换jobList并且返回替换后的value
	private static List<TableJob> replacejobListIdByMap(List<TableJob> jobList) {
		int i = 0;
		List<TableJob> jobListTemp = new ArrayList();
		for (TableJob jobFor : jobList) {
			jobListTemp.add(jobFor);
			for (String keyString : idReplaceMap.keySet()) {
				
				for(String jobKeyFor:jobFor.keys.keySet())
				{
					if (!keyString.equals(jobFor.keys.get(jobKeyFor)))
					{
						jobFor.keys.put(jobKeyFor, idReplaceMap.get(keyString));
						jobListTemp.set(i, jobFor);
					}
					
				}
			}
			i++;
		}
		
		return jobListTemp;
		
	}

	// $构造替换map并且返回替换后的value
	private static StringBuffer constructMapAndReplaceValue(
			StringBuffer columnName, StringBuffer columnValue) {

		String columnValueString = columnValue.toString();
		List<String> nameList = Arrays.asList(columnName.toString().split(","));
		List<String> valueList = Arrays.asList(columnValue.toString()
				.split(","));
		for (String keyString : keyList) {
			for (String nameString : nameList) {
				if (nameString.equals(keyString)) {
					String mapValue = valueList.get(nameList
							.indexOf(nameString));
					String replaceValue = java.util.UUID.randomUUID()
							.toString().replace("-", "");
					replaceValue = "'" + replaceValue + "'";
					// 之前的value存在直接替换
					if (idReplaceMap.keySet().contains(mapValue)) {
						columnValueString = columnValueString.replaceAll(
								mapValue, idReplaceMap.get(mapValue));
						break;
					} else {
						columnValueString = columnValueString.replaceAll(
								mapValue, replaceValue);
					}
					idReplaceMap.put(mapValue, replaceValue);
					// System.out.println("\n"+nameString+":"+mapValue+":"+replaceValue);

					break;
				}
			}
		}
		// System.out.println(columnValueString+"\n");
		return new StringBuffer(columnValueString);

	}

	// $根据主键MAP替换大字段list
	private static void constructBlobList() {
		for (TableJob jobListfor : jobList) {
			Map<String, InputStream> blobsFor = jobListfor.blobs;
			Iterator bolbsForIt = (Iterator) blobsFor.keySet();
			while (bolbsForIt.hasNext()) {
				String bolbskeyString = (String) bolbsForIt.next();
			}
		}

	}

	/**
	 * 把sql输出的文件
	 */
	private static void createFile(List<String> sqlList, String filePath) {
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
				for (int i = 0; i < sqlList.size(); i++) {
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
	private static String concatInsertSQL(String TableName,
			StringBuffer ColumnName, StringBuffer ColumnValue) {
		StringBuffer insertSQL = new StringBuffer();
		insertSQL.append("insert into ").append(TableName).append("(")
				.append(ColumnName.toString()).append(")").append(" values")
				.append("(").append(ColumnValue.toString()).append(");");
		return insertSQL.toString();
	}

	public static void main(String[] args) throws Exception {
		
		String sqlPath = "D:/DISK/SQL/";
		System.out.println(System.currentTimeMillis() + ":"
				+ MysqlWFTools.class.getName() + ":开始读取查询语句配置文件");
		List<String> list = readInputFile(sqlPath + "input.sql");
		System.out.println(System.currentTimeMillis() + ":"
				+ MysqlWFTools.class.getName() + ":开始执行查询语句");
		exportInserSql(list);
		System.out.println(System.currentTimeMillis() + ":"
				+ MysqlWFTools.class.getName() + ":执行查询语句完成");
		createFile(execSqlList, sqlPath + "output_s1.sql");
		System.out.println(System.currentTimeMillis() + ":"
				+ MysqlWFTools.class.getName() + ":输出查询语句完成");
		createFile(outSqlList, sqlPath + "/output_i1.sql");
		System.out.println(System.currentTimeMillis() + ":"
				+ MysqlWFTools.class.getName() + ":输出insert语句完成");
		System.out.println(keyList);
		System.out.println(idReplaceMap.keySet());
		System.out.println(idReplaceMap.values());
		
		if(!copyOrNot)
		{
			outSqlList = replaceIdByMap(outSqlList);
			jobList = replacejobListIdByMap(jobList);
		}
		createFile(outSqlList, sqlPath
				+ "/output_iRepalceId.sql");
		System.out.println(System.currentTimeMillis() + ":"
				+ MysqlWFTools.class.getName() + ":输出替换ID的insert语句完成");

		System.out.println(System.currentTimeMillis() + ":"
				+ MysqlWFTools.class.getName() + ":开始在目标库执行insert语句");
		
		//execInsertSql(outSqlList);
		//execInsertSql(outSqlList);
		System.out.println(System.currentTimeMillis() + ":"
				+ MysqlWFTools.class.getName() + ":在目标库执行insert语句完成");
		System.out.println(jobList);

	}

}
