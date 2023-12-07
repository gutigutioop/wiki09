package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import constants.Const;

/**
 * Daoクラス - 各daoの基底クラス
 * データベースへの接続、切断、クエリ実行など各dao共通の機能を実装する
 * @author　Say Consulting Group
 * @version　1.0.0
 */
public class Dao {

  /** DB接続オブジェクト */
  protected Connection con;
  /** SQL実行用オブジェクト */
  protected PreparedStatement stm;
  /** 対象テーブル物理名 */
  protected String table;
  
  /**
   * コンストラクタ
   * DB接続用のフィールドを初期化する null別に使わなくて良い。念のため使用。インスタントはnullで初期化。変数宣言は初期化？
   */
  public Dao() {
    this.con = null;
    this.stm = null;
  }

  /**
   * openメソッド
   * データベースへの接続を開始する
   */
  protected void open() {
    try {
     // Class.forName("com.mysql.cj.jdbc.Driver");
      this.con = DriverManager.getConnection(Const.DB_URI, Const.DB_USER, Const.DB_PASS);
      System.out.println("データベースへの接続に成功しました。");
    } catch (SQLException e) {
      System.err.println("データベースへの接続に失敗しました。");
      System.err.println(e);
   // } catch (ClassNotFoundException e) {
     // System.err.println(e);
    }
  }

  /**
   * closeメソッド
   * DBとの接続を切断する
   * 各オブジェクトを再初期化する
   */
  protected void close() {
    try {
      if (this.stm != null) {
        this.stm.close();
        this.stm = null;
      }
      if (this.con != null) {
        this.con.close();
        this.con = null;
      }
      System.out.println("データベース接続の切断に成功しました。");
    } catch (SQLException e) {
      System.err.println("データベース接続の切断に失敗しました。");
      System.err.println(e);
    }
  }
  
  /**
   * executeUpdateメソッド
   * DBテーブルの更新sqlを実行する
   * @param sql 更新を実行するsql
   */
  public void executeUpdate(String sql) {
    try {
      this.stm = this.con.prepareStatement(sql);
      // 実行SQLの表示
      System.out.println("SQL:" + this.stm.toString());  //クラス：実行を表す。
      this.stm.executeUpdate();
      System.out.println("更新SQLの実行に成功しました。");
    } catch (SQLException e) {
      System.err.println("更新SQLの実行に失敗しました。");
      System.err.println(e);
    }
  }
 
  /**
   * clearメソッド　　★clearメソッドはexecuteUpdateメソッドを使って、テーブルの中身を空にしてとDBに更新を命令している。メインクラスでclearメソッドを使う。executeUpdateメソッドは更新するため作った
   * 担当テーブルをtruncate(全件削除)する　　truncateは、DBの中身をクリアする。テーブルは残っている。テーブルの中のデータのみ削除。箱のみ残っている。凹
   * clearメソッドは、executeUpdateメソッドを使っている（呼んでる）executeUpdateは、DBの中身更新するときのコマンド実行。SQL実行のためのOBJ
   */
  public void clear() {
    open();
    String sql = String.format("truncate %s;", this.table);
    executeUpdate(sql);
    close();
  }
}
