package dto;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import constants.Color;

/**
 * WikiDtoクラス
 * wikiデータを一つ持てるdata object(dto)
 * Java Beansの定石通り直列化用インターフェース実装
 * @author　Say Consulting Group
 * @version　1.0.0
 */
public class WikiDto implements Serializable {

  /** wikiの連番 */
  private int id;
  /** 題名 */
  private String title;
  /** 本文 */
  private String body;
  
  /**
   * 引数無しのコンストラクタ
   */
  public WikiDto() {
    super();
  }

  /**
   * 引数に連番とwikiファイルを指定するコンストラクタ
   * 連番とwikiが設定されたdtoインスタンスを生成する
   * @param id wikiの連番
   * @param file 読み込み対象のfile
   */
  public WikiDto(int id, File file) {
    // 連番を設定
    this.id = id;

    // wiki題名をファイルから取得してフィールドに設定
    this.title = file.getName();

    // 指定されたファイルの絶対パスを取得
    Path path = Paths.get(file.getAbsolutePath());
    
    // wiki本文をファイルから取得してフィールドに設定
    // ファイルを読み込む際、IOExceptionが発生する可能性があるためtry～catch構文を使う
    try {
      this.body = Files.readString(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * getIdメソッド
   * idのフィールド値を返す
   * @return idフィールドの値
   * */
  public int getId() {
    return this.id;
  }
  
  /**
   * getTitleメソッド
   * titleのフィールド値を返す
   * @return titleフィールドの値
   * */
  public String getTitle() {
    return this.title;
  }
  
  /**
   * getBodyメソッド
   * bodyのフィールド値を返す
   * @return bodyフィールドの値
   * */
  public String getBody() {
    return this.body;
  }

  /**
   * setIdメソッド
   * idのフィールド値を設定する
   * @param idフィールドの値
   * */
  public void setId(int id) {
    this.id = id;
  }
  
  /**
   * setTitleメソッド
   * titleのフィールド値を返す
   * @param titleフィールドの値
   * */
  public void setTitle(String title) {
    this.title = title;
  }
  
  /**
   * setBodyメソッド
   * bodyのフィールド値を返す
   * @param bodyフィールドの値
   * */
  public void setBody(String body) {
    this.body = body;
  }

  /**
   * indexメソッド
   * wikiの連番と題名の文字列を返す
   * @return 【連番3桁d : 題名】左記形式に整形された文字列
   */
  public String index() {
    String ids = String.format("%3d", this.id);
    return String.format("【%s : %s】", Color.Green.coloredStr(ids), Color.Yellow.coloredStr(this.title));
  }

  /**
   * toStringメソッド
   * 上記indexメソッドの戻り値に、wiki本文を加えた文字列を返す
   * @return 【連番3桁d : 題名】+ 改行 + wiki本文
   */
  public String toString() {
    return String.format("%s\n%s\n", this.index(), this.body);
  }
  
  /**
   * isMatchedメソッド
   * 引数の文字列を含むwikiかの真偽値を返す
   * @param q 検索文字列
   * @return 検索文字列を含む場合:true それ以外：false
   */
  public boolean isMatched(String q) {
    return 0 <= this.title.indexOf(q) || 0 <= this.body.indexOf(q);
  }
  
}
