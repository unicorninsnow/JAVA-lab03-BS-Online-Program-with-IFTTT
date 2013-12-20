package background;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.ResultSetMetaData;

/**
 * 数据库查询得到的Result转json数据<br>
 * 以方便ajax处理
 * @author mzs
 *
 */
public class ResultSet2Json {
	
    /**
     *数据库查询结果集到json的转换<br>
     *为空则只传回[] 
     * @param rs
     * 结果集参数
     * @return
     * @throws SQLException
     * @throws JSONException
     */
    public static String resultSetToJson(ResultSet rs) throws SQLException, JSONException 
    {
       // json数组
       JSONArray array = new JSONArray();
      
       // 获取列数
       ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
       int columnCount = metaData.getColumnCount();
      
       // 遍历ResultSet中的每条数据
        while (rs.next()) {
            JSONObject jsonObj = new JSONObject();
           
            // 遍历每一列
            for (int i = 1; i <= columnCount; i++) {
                String columnName =metaData.getColumnLabel(i);
                String value = rs.getString(columnName);
                jsonObj.put(columnName, value);
            } 
            array.put(jsonObj); 
        }
      
       return array.toString();
    }
}
