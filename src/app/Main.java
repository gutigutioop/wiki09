package app;

import java.util.List;

import constants.Color;
import constants.Const;
import dto.WikiDto;
import model.WikiReader;
import model.dao.WikiDao;

/**
 * DBベースWikiアプリ
 * 
 * Wikiアプリ開発09 - wikiをDBに入れよう！
 *
 * Mainクラス
 * DBベースWikiアプリの実行エントリポイントを持つクラス
 * @author　Say Consulting Group
 * @version　1.0.0
 */
public class Main {
  
  /**
   * mainメソッド
   * DBベースwikiアプリのエントリポイント
   * @param args コマンドライン引数(未使用)　　メインクラスでは、clearとaddAllメソッドは、executeUpdate（更新に関する）メソッドを使ってる。
   */
  public static void main(String[] args) {

    // ウェルカムメッセージ表示
    System.out.println(Const.WELCOME_MESSAGE);

    // wikiのリスト読み込み
    List<WikiDto> list = WikiReader.getAll();//WikiReaderにカーソル合わせてインポートしたらエラー消えた。
    WikiDao dao = new WikiDao();
    // テーブルをクリアする
    dao.clear();
    // wikiを全件追加する
    dao.addAll(list);

    /*
    List<WikiDto> list = dao.getAll();

    // 各値初期化
    String inpStr = "";
    int inpVal = 0;
    int listSize = list.size();
    boolean next = true;

    // 標準入力のスキャナーを生成
    try (Scanner sc = new Scanner(System.in)) {
      // キーボードから0が入力されるまでループする
      do {
        // wikiのリスト表示
        showIndex(list);
        System.out.print(Const.IMPUT_MESSAGE);
        // キーボードからの入力を受け取り変数に格納
        inpStr = sc.nextLine();

        try {
          inpVal = Integer.parseInt(inpStr);

          if (inpVal > listSize || listSize < 0) {
            System.out.println(String.format("0～%dまでの数値を入力してください。", listSize));
          } else if (inpVal == 0) {
            // 終了する
            next = false;
          } else {
            // 指定番号のwikiを表示
            WikiDto d = list.get(inpVal - 1);
            System.out.println(d.toString());
          }
        } catch (NumberFormatException e) {
          // 入力が数字ではなかった場合、全文検索を行う
          searchWiki(list, inpStr);
        }

      } while (next);
    }

    // 終了メッセージを出力
    System.out.println(Const.FAIRWELL_MESSAGE);
    */
  }

  /**
   * showIndexメソッド
   * wikiのリストを表示する
   * 繰り返し実行される一覧表示処理を外出しし、mainメソッドのコードを読みやすくする
   * 一行に表示するwikiの数はConstクラスの定数で変更可能
   * @param list wikiDtoのリスト
   */
  private static void showIndex(List<WikiDto> list) {

    int i = 0;
    for (WikiDto item : list) {
      System.out.print(item.index());

      // 一定数の表示後は改行し、改行しない場合は余白を表示
      String margin = i % Const.INDEX_LENGTH == Const.INDEX_LENGTH - 1 ? "\n\n" : "  ";
      System.out.print(margin);
      i++;
    }
  }
  
  /**
   * searchWikiメソッド
   * 指定文字列をタイトルか本文に含むwikiを表示する
   * @param list wikiDtoのリスト
   * @param q 検索文字列
   */
  private static void searchWiki(List<WikiDto> list, String q) {
    int i = 0;
    for (WikiDto item : list) {
      if (item.isMatched(q)) {
        System.out.println(item);
        i++;
      }
    }
    
    // 一件もヒットしなかった場合はその旨表示する
    if (0 == i) {
      System.out.println(Color.Red.coloredStr("一致するwikiが見つかりませんでした。\n"));
    }
  }
}
