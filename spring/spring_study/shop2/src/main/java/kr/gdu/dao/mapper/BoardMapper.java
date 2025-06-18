package kr.gdu.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.gdu.logic.Board;

@Mapper
public interface BoardMapper {
    String select = "select num,writer,pass,title,content,file1 fileurl,"
		+ " regdate, readcnt, grp, grplevel, grpstep, boardid from board";
   
    @Select({"<script>",
   	"select count(*) from board where boardid=#{boardid} ",
    "<if test='searchtype != null'> "
    + " and ${searchtype} like '%${searchcontent}%'</if>",
   	"</script>"})
	int count(Map<String, Object> param);
    
    @Select({"<script>",
    	select,
   	"<if test='num != null'> where num = #{num}</if>",
   	"<if test='boardid != null'> where boardid = #{boardid} </if>",
   	"<if test='searchtype != null'> "
   	+ " and ${searchtype} like '%${searchcontent}%'</if>",
   	"<if test='limit != null'> "
   	+ " order by grp desc, grpstep asc limit #{startrow}, #{limit}</if>",
   	"</script>"})    
	List<Board> select(Map<String, Object> param);

	@Update("update board set readcnt = readcnt + 1 where num=#{num}")
	void addReadcnt(Map<String, Object> param);

	@Select("select ifnull(max(num),0) from board")
	int maxNum();

	@Insert("insert into board "
			+ "(num,boardid,writer,pass,title,content,file1,"
			+ "regdate,readcnt,grp,grplevel,grpstep) values("
			+ "#{num},#{boardid},#{writer},#{pass},#{title},#{content},"
			+ "#{fileurl},now(),0,#{grp},#{grplevel},#{grpstep})")
	void insert(Board board);

	@Update("update board set writer=#{writer}, title=#{title}, content=#{content},"
			+ " file1=#{fileurl} where num=#{num}")
	void update(Board board);

	@Delete("delete from board where num=#{value}")
	void delete(int num);

	@Update("update board set grpstep=grpstep+1"
			+" where grp = #{grp} and grpstep > #{grpstep}")
	void grpStepAdd(int grp, int grpstep);
/*
 * [{writer="홍길동", cnt:10},{writer="김삿갓", cnt:7},...]
 *  => [{홍길동=10}, {김삿갓=7},...] 형식으로 브라우저에 전달
 */
	
	@Select("select writer, count(*) cnt from board where boardid=#{value} "
			+ " group by writer order by 2 desc limit 0,7")
	List<Map<String, Object>> graph1(String id);

			
	@Select("select date_format(regdate,'%Y-%m-%d') day, count(*) cnt "
			+ "from board "
			+ "where boardid=#{value} "
			+ " group by date_format(regdate,'%Y-%m-%d')"
			+ " order by 2 desc limit 0,7")
	List<Map<String, Object>> graph2(String id);


	
	
	

}
