package model.dao;

import java.util.List;

import dto.WikiDto;

public class WikiDao extends Dao {
  
  /** このDaoが担当するテーブル物理名 */
  private static final String MY_TABLE = "wikis";
  
  /**
   * コンストラクタ
   * 自身が担当するテーブル物理名を設定
   */
  public WikiDao() {
    super();
    this.table = MY_TABLE;
  }

  
  /**DAOの継承クラスなので、executeUpdateを宣言なしで使える。
   * addメソッド　　addの中でexecuteUpdateメソッドを呼んでる。
   * 引数のWikiDtoのフィールド値をDBのテーブルに追加する
   * @param dto 追加するWikiDto
   */
  protected void add(WikiDto dto) {
    String sql = String.format("insert into %s(title, body) values('%s', '%s');", table, dto.getTitle(), dto.getBody());
    executeUpdate(sql);
  }
  
  /**
   * addAllメソッド→addAllは、テーブルの中空にしてから、wikiで読み込んだ内容もう一度を箱に入れている。addAllは、addを呼んでる。40行目が27行目に飛ぶ。
   * 引数のWikiDtoのListにあるwikiを全てDBのテーブルに追加する
   * @param list 追加するWikiDtoのArrayList
   */
  public void addAll(List<WikiDto> list) {
    open();
    for(WikiDto item : list) {
      add(item);
    }
    close();
  }
}
