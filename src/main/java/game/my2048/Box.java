package game.my2048;

import java.util.Random;

/**
 * Created by johnny on 2016/10/6.
 *
 */
public class Box {
    int square[][] = new int[4][4];
    int location[] = new int[16];
    int suiji = 16;
    //GridLayout layout = new GridLayout(4,4,1,1);
    //JFrame frame = new JFrame();
    public Box(){
        for(int i = 0;i<4;i++){
            for(int j = 0;j<4;j++){
                square[i][j] = 0;
            }
        }
        for(int n = 0;n<16;n++){
            location[n] = n;
        }
        run();
        System.out.println(suiji);
        dayin();
    }
    public int End(int squ[][]){
        int jud = 0;
        int sum = 0;
        int num[] = new int[8];
        num[0] = JudgeEnd(squ[0][0],squ[0][1],squ[0][2],squ[0][3]);
        num[1] = JudgeEnd(squ[1][0],squ[1][1],squ[1][2],squ[1][3]);
        num[2] = JudgeEnd(squ[2][0],squ[2][1],squ[2][2],squ[2][3]);
        num[3] = JudgeEnd(squ[3][0],squ[3][1],squ[3][2],squ[3][3]);
        num[4] = JudgeEnd(squ[0][0],squ[1][0],squ[2][0],squ[3][0]);
        num[5] = JudgeEnd(squ[0][1],squ[1][1],squ[2][1],squ[3][1]);
        num[6] = JudgeEnd(squ[0][2],squ[1][2],squ[2][2],squ[3][2]);
        num[7] = JudgeEnd(squ[0][3],squ[1][3],squ[2][3],squ[3][3]);
        for(int i = 0;i<8;i++){
            sum = num[0]+sum;
        }
        if(sum==8){
            jud = 1;
        }
        return jud;
    }
    private int JudgeEnd(int num1,int num2,int num3,int num4){
        int flag = 0;
        if(num1!=num2&&num2!=num3&&num3!=num4&&num1!=0&&num2!=0&&num3!=0&&num4!=0){
            flag = 1;
        }
        return flag;
    }
    public int Score(int squ[][]){
        int sum = 0;
        for(int i = 0;i<4;i++){
            for(int j = 0;j<4;j++){
                sum = sum+squ[i][j];
            }
        }
        sum = 5*sum;
        return sum;
    }
    private int[] JudgeSame(int num1,int num2,int num3,int num4){
        int num[] = new int[4];
        for(int i = 0;i<4;i++){
            num[i] = 0;
        }
        if(num1==num2){
            num1 = num2+num1;
            num2 = num3;
            num3 = num4;
            num4 = 0;
        }
        if(num2==num3){
            num2 = num3+num2;
            num3 = num4;
            num4 = 0;
        }
        if(num3==num4){
            num3 = num3+num4;
            num4 = 0;
        }
        num[0] = num1;
        num[1] = num2;
        num[2] = num3;
        num[3] = num4;
        return num;
    }
    private int[] JudgeZero(int num1,int num2,int num3,int num4){
        int num[] = new int[4];
        for(int i = 0;i<4;i++){
            num[i] = 0;
        }
        int count = 0;
        if(num1!=0){
            num[count] = num1;
            count++;
        }
        if(num2!=0){
            num[count] = num2;
            count++;
        }
        if(num3!=0){
            num[count] = num3;
            count++;
        }
        if(num4!=0){
            num[count] = num4;
        }
        return num;
    }
    public void show(){
        for(int i = 0;i<4;i++){
            for(int j = 0;j<4;j++){
                System.out.print(square[i][j]);
            }
            System.out.println("");
        }
    }
    public void control(int flag){
        int number[] = new int[4];
        for(int i = 0;i<4;i++){
            number[i] = 0;
        }
        if(flag==1){//1是上
            number = JudgeZero(square[0][0],square[1][0],square[2][0],square[3][0]);
            square[0][0] = number[0];
            square[1][0] = number[1];
            square[2][0] = number[2];
            square[3][0] = number[3];
            number = JudgeZero(square[0][1],square[1][1],square[2][1],square[3][1]);
            square[0][1] = number[0];
            square[1][1] = number[1];
            square[2][1] = number[2];
            square[3][1] = number[3];
            number = JudgeZero(square[0][2],square[1][2],square[2][2],square[3][2]);
            square[0][2] = number[0];
            square[1][2] = number[1];
            square[2][2] = number[2];
            square[3][2] = number[3];
            number = JudgeZero(square[0][3],square[1][3],square[2][3],square[3][3]);
            square[0][3] = number[0];
            square[1][3] = number[1];
            square[2][3] = number[2];
            square[3][3] = number[3];
            number = JudgeSame(square[0][0],square[1][0],square[2][0],square[3][0]);
            square[0][0] = number[0];
            square[1][0] = number[1];
            square[2][0] = number[2];
            square[3][0] = number[3];
            number = JudgeSame(square[0][1],square[1][1],square[2][1],square[3][1]);
            square[0][1] = number[0];
            square[1][1] = number[1];
            square[2][1] = number[2];
            square[3][1] = number[3];
            number = JudgeSame(square[0][2],square[1][2],square[2][2],square[3][2]);
            square[0][2] = number[0];
            square[1][2] = number[1];
            square[2][2] = number[2];
            square[3][2] = number[3];
            number = JudgeSame(square[0][3],square[1][3],square[2][3],square[3][3]);
            square[0][3] = number[0];
            square[1][3] = number[1];
            square[2][3] = number[2];
            square[3][3] = number[3];
            Reset();
            //dayin();
        }
        if(flag==2){//2是下
            number = JudgeZero(square[3][0],square[2][0],square[1][0],square[0][0]);
            square[3][0] = number[0];
            square[2][0] = number[1];
            square[1][0] = number[2];
            square[0][0] = number[3];
            number = JudgeZero(square[3][1],square[2][1],square[1][1],square[0][1]);
            square[3][1] = number[0];
            square[2][1] = number[1];
            square[1][1] = number[2];
            square[0][1] = number[3];
            number = JudgeZero(square[3][2],square[2][2],square[1][2],square[0][2]);
            square[3][2] = number[0];
            square[2][2] = number[1];
            square[1][2] = number[2];
            square[0][2] = number[3];
            number = JudgeZero(square[3][3],square[2][3],square[1][3],square[0][3]);
            square[3][3] = number[0];
            square[2][3] = number[1];
            square[1][3] = number[2];
            square[0][3] = number[3];
            number = JudgeSame(square[3][0],square[2][0],square[1][0],square[0][0]);
            square[3][0] = number[0];
            square[2][0] = number[1];
            square[1][0] = number[2];
            square[0][0] = number[3];
            number = JudgeSame(square[3][1],square[2][1],square[1][1],square[0][1]);
            square[3][1] = number[0];
            square[2][1] = number[1];
            square[1][1] = number[2];
            square[0][1] = number[3];
            number = JudgeSame(square[3][2],square[2][2],square[1][2],square[0][2]);
            square[3][2] = number[0];
            square[2][2] = number[1];
            square[1][2] = number[2];
            square[0][2] = number[3];
            number = JudgeSame(square[3][3],square[2][3],square[1][3],square[0][3]);
            square[3][3] = number[0];
            square[2][3] = number[1];
            square[1][3] = number[2];
            square[0][3] = number[3];
            Reset();
            //dayin();
        }
        if(flag==3){//3是左
            number = JudgeZero(square[0][0],square[0][1],square[0][2],square[0][3]);
            square[0][0] = number[0];
            square[0][1] = number[1];
            square[0][2] = number[2];
            square[0][3] = number[3];
            number = JudgeZero(square[1][0],square[1][1],square[1][2],square[1][3]);
            square[1][0] = number[0];
            square[1][1] = number[1];
            square[1][2] = number[2];
            square[1][3] = number[3];
            number = JudgeZero(square[2][0],square[2][1],square[2][2],square[2][3]);
            square[2][0] = number[0];
            square[2][1] = number[1];
            square[2][2] = number[2];
            square[2][3] = number[2];
            number = JudgeZero(square[3][0],square[3][1],square[3][2],square[3][3]);
            square[3][0] = number[0];
            square[3][1] = number[1];
            square[3][2] = number[2];
            square[3][3] = number[3];
            number = JudgeSame(square[0][0],square[0][1],square[0][2],square[0][3]);
            square[0][0] = number[0];
            square[0][1] = number[1];
            square[0][2] = number[2];
            square[0][3] = number[3];
            number = JudgeSame(square[1][0],square[1][1],square[1][2],square[1][3]);
            square[1][0] = number[0];
            square[1][1] = number[1];
            square[1][2] = number[2];
            square[1][3] = number[3];
            number = JudgeSame(square[2][0],square[2][1],square[2][2],square[2][3]);
            square[2][0] = number[0];
            square[2][1] = number[1];
            square[2][2] = number[2];
            square[2][3] = number[2];
            number = JudgeSame(square[3][0],square[3][1],square[3][2],square[3][3]);
            square[3][0] = number[0];
            square[3][1] = number[1];
            square[3][2] = number[2];
            square[3][3] = number[3];
            Reset();
            //dayin();
        }
        if(flag==4){//是右
            number = JudgeZero(square[0][3],square[0][2],square[0][1],square[0][0]);
            square[0][3] = number[0];
            square[0][2] = number[1];
            square[0][1] = number[2];
            square[0][0] = number[3];
            number = JudgeZero(square[1][3],square[1][2],square[1][1],square[1][0]);
            square[1][3] = number[0];
            square[1][2] = number[1];
            square[1][1] = number[2];
            square[1][0] = number[3];
            number = JudgeZero(square[2][3],square[2][2],square[2][1],square[2][0]);
            square[2][3] = number[0];
            square[2][2] = number[1];
            square[2][1] = number[2];
            square[2][0] = number[3];
            number = JudgeZero(square[3][3],square[3][2],square[3][1],square[3][0]);
            square[3][3] = number[0];
            square[3][2] = number[1];
            square[3][1] = number[2];
            square[3][0] = number[3];
            number = JudgeSame(square[0][3],square[0][2],square[0][1],square[0][0]);
            square[0][3] = number[0];
            square[0][2] = number[1];
            square[0][1] = number[2];
            square[0][0] = number[3];
            number = JudgeSame(square[1][3],square[1][2],square[1][1],square[1][0]);
            square[1][3] = number[0];
            square[1][2] = number[1];
            square[1][1] = number[2];
            square[1][0] = number[3];
            number = JudgeSame(square[2][3],square[2][2],square[2][1],square[2][0]);
            square[2][3] = number[0];
            square[2][2] = number[1];
            square[2][1] = number[2];
            square[2][0] = number[3];
            number = JudgeSame(square[3][3],square[3][2],square[3][1],square[3][0]);
            square[3][3] = number[0];
            square[3][2] = number[1];
            square[3][1] = number[2];
            square[3][0] = number[3];
            Reset();
            //dayin();
        }
    }
    private int SetRandom(){
        int num;
        int local;
        Random rand = new Random();
        num = rand.nextInt(suiji);
        local = location[num];
        for(int i = num;i<suiji-1;i++){
            location[i] = location[i+1];
        }
        location[suiji-1] = local;
        suiji--;
        //System.out.println(suiji);
        // System.out.println(num);
        //dayin();
        return local;
    }

    private int[] getLocation(int num){
        int loca[] = new int[2];
        loca[0] = (int)(num/4);
        loca[1] = num%4;
        return loca;
    }

    public void Reset(){
        int count1 = 0;
        int count2 = 0;
        for(int i = 0;i<4;i++){
            for(int j = 0;j<4;j++){
                if(square[i][j]==0){
                    location[count1] = i*4+j;
                    count1++;
                }
                if(square[i][j]!=0){
                    location[15-count2] = i*4+j;
                    count2++;
                }
            }
        }
        suiji = 16-count2;
        System.out.println(suiji);
        dayin();
    }

    private void run(){
        int num[] = new int[2];
        int num1 = SetRandom();
        int num2 = SetRandom();
        int num3 = SetRandom();
        num = getLocation(num1);
        square[num[0]][num[1]] = 2;
        num = getLocation(num2);
        square[num[0]][num[1]] = 2;
        num = getLocation(num3);
        square[num[0]][num[1]] = 2;
    }

    public void getsuiji(){
        int num[] = new int[2];
        int num1 = SetRandom();
        num = getLocation(num1);
        square[num[0]][num[1]] = 2;
        Reset();
    }

    public void dayin(){
        for(int i = 0;i<16;i++){
            System.out.print(location[i]+" ");
        }
        System.out.println("");
    }
}
