package project.game.poker;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

//可改变玩家数量和玩家手牌数,根据手牌中最大牌进行简单大小比较
public class PokerGame {
    //http://www.imooc.com/qadetail/80811
    //bug:http://www.imooc.com/qadetail/71907
    public static void main(String[] args) {
        System.out.println("--------产生一副扑克牌--------");
        List<Poker> pockers = Poker.createPocker();
        System.out.println("--------扑克牌创建成功--------");
        System.out.println(pockers.toString());
        System.out.println("--------开始洗牌--------");
        Collections.shuffle(pockers);
        System.out.println("--------洗牌结束--------");
        System.out.println(pockers.toString());
        System.out.println("--------创建玩家--------");
        Scanner scanner = new Scanner(System.in);
        List<Player> players = new ArrayList<>();
        int handCardNum = 3;
        int playerNum = 2;
        while (true) {
            try {
                System.out.println("请输入玩家数量：");
                playerNum = scanner.nextInt();
                int id;
                String name;
                for (int i = 0; i < playerNum; i++) {
                    System.out.println("创建第" + (i + 1) + "位玩家的ID和姓名：");
                    System.out.println("输入玩家id(int): ");
                    id = scanner.nextInt();
                    System.out.println("输入玩家姓名：");
                    name = scanner.next();
                    players.add(new Player(id, name));
                }
                for (int i = 0; i < playerNum; i++) {
                    System.out.println("欢迎玩家：" + players.get(i).getName());
                }
                System.out.println("--------开始发牌--------");
                int cardNums = handCardNum * playerNum;
                Poker pocker;
                for (int i = 0; i < cardNums; i++) {
                    pocker = pockers.get(i);
                    players.get(i % playerNum).getHandPockers().add(pocker);
                }
                System.out.println("--------发牌结束--------");
                System.out.println("--------开始游戏--------");
                Poker maxPocker = new Poker("方块", "2");
                int winPlayer = 0;
                for (int i = 0; i < playerNum; i++) {
                    Collections.sort(players.get(i).getHandPockers()); // 对手牌进行排序
                    Collections.reverse(players.get(i).getHandPockers()); // 排序后的手牌进行翻转，第一位为最大值
                    System.out
                            .println("玩家"
                                    + players.get(i).getName()
                                    + "最大手牌为："
                                    + players.get(i).getHandPockers().get(0)
                                    .toString());
                    if (maxPocker.compareTo(players.get(i).getHandPockers()
                            .get(0)) < 0) {
                        maxPocker = players.get(i).getHandPockers().get(0);
                        winPlayer = i;
                    }
                }
                System.out.println("玩家" + players.get(winPlayer).getName()
                        + "获胜");
                System.out.println("玩家各自的手牌为：");
                for (int i = 0; i < playerNum; i++) {
                    System.out.println(players.get(i).getName() + ": "
                            + players.get(i).getHandPockers().toString());
                }
                break;
            } catch (Exception e) {
                System.out.println("输入值错误，请重新输入");
                scanner = new Scanner(System.in);
            }
        }
        scanner.close();
    }
}

@Data
class Player {
    /*
     * 玩家
     * 属性：ID，姓名，手牌
     * 手牌为扑克牌的集合
     */
    private int id;
    private String name;
    private List<Poker> handPockers;

    Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.handPockers = new ArrayList<>();
    }
}

/*
 *  poker class, 
 *  包括四种花色：黑桃、红桃、梅花、方块、
 *  十三中点数：2~10，J、Q、K、A，不包括大小王
 */
class Poker implements Comparable<Poker> {
    private String color;  //扑克牌花色
    private String value;  //扑克牌面值
    private static final int CARDNUM = 52;   //扑克牌总量
    private static final int SINGLECOLORNUM = 13; //每种颜色扑克牌数量

    public Poker() {
    }

    Poker(String color, String value) {
        this.color = color;
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public String getValue() {
        return value;
    }
 
    /*
     * 产生52张扑克牌
     * return List<Pocker>
     */

    static List<Poker> createPocker() {
        List<Poker> pockers = new ArrayList<>();
        for (int i = 0; i < CARDNUM; i++) {
            String newValue;
            int temp = i % SINGLECOLORNUM;
            switch (temp) {
                case 11:
                    newValue = "j";
                    break;
                case 12:
                    newValue = "Q";
                    break;
                case 0:
                    newValue = "K";
                    break;
                case 1:
                    newValue = "A";
                    break;
                default:
                    newValue = String.valueOf(temp);
            }

            String newColor = null;
            switch (i / SINGLECOLORNUM) {
                case 0:
                    newColor = "黑桃";
                    break;
                case 1:
                    newColor = "红桃";
                    break;
                case 2:
                    newColor = "梅花";
                    break;
                case 3:
                    newColor = "方块";
                default:
            }
            pockers.add(new Poker(newColor, newValue));
        }
        return pockers;
    }

    /**
     * Collections.shuffle进行洗牌
     */
    public static List<Poker> shuffle(List<Poker> pockers) {
        Collections.shuffle(pockers);
        return pockers;
    }

    /**
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     * 重写了copmareTo方法，实现了Comparable接口
     * 根据牌值和花色在列表中的顺序进行比较
     */
    @Override
    public int compareTo(Poker o) {
        List<String> valueBase = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A");
        List<String> colorBase = Arrays.asList("方块", "梅花", "红桃", "黑桃");
        if (valueBase.indexOf(this.value) == valueBase.indexOf(o.value)) {
            return Integer.compare(colorBase.indexOf(this.color), colorBase.indexOf(o.getColor()));
        } else {
            return Integer.compare(valueBase.indexOf(this.value), valueBase.indexOf(o.getValue()));
        }
    }

    @Override
    public String toString() {
        return color + value;
    }
}