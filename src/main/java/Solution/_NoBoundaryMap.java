package Solution;

import Common.Point;

import java.util.*;

/**
 * Created by gaolei on 16/1/21.
 * 千里码编码63的题目
 * 这个题主要就是练练编程，思路比较容易理清楚，既然是无边界的地图，那我们肯定就不会存地图了
 * 记得图论里的算法存图有两种方式，一种是二维数组，一种就是记所有点和边。这里我们记下点就行了。
 * Map<Point,Cnt>标识某个点周围的生命体数量
 * 每一次变化，记两个Map<Point,Cnt>，一个Map中是生物，另一个是空地。根据计算结果和规则，很容易得知这一轮过去后有哪些点在下一轮是生物
 * 生命体在代码中用human 标识
 * test github
 * branch dev
 */
public class _NoBoundaryMap {
    public static final int MagicNum = 10000;
    public static void main(String[] args) {
        List<Point> humanList = new ArrayList<Point>();

        //初始化
        humanList.add(new Point(0,1));
        humanList.add(new Point(1,0));
        humanList.add(new Point(1,1));
        humanList.add(new Point(1,2));
        humanList.add(new Point(2,2));


        int Max = 0;
        int MaxIndex = -1;
        for (int generations = 1; generations <= MagicNum; generations ++) {
            //humanList的人，放到Map中, 初始化human这个Map
            Map<Point,Integer> humanMap = new HashMap<Point, Integer>();
            for(Point point : humanList) {
                humanMap.put(point, 0);
            }
            //空地的map
            Map<Point, Integer> spaceMap = new HashMap<Point, Integer>();
            //循环humanMap，每个人的周围八个点的cnt ++，先判断这个点是不是human，不是的话，就放空地。
            for(Point human : humanList) {
                int x = human.getX();
                int y = human.getY();
                for(int xMove = -1; xMove <= 1; xMove++) {
                    for(int yMove = -1; yMove <= 1; yMove++) {
                        //xMove 和 yMove 都是0说明是原点
                        if(xMove == 0 && yMove == 0)
                            continue;
                        Point point = new Point(x+xMove, y+yMove);
                        //判断这个point是否在humanMap中，如果在，则对应value +1
                        if(humanMap.containsKey(point)) {
                            Integer cnt = humanMap.get(point);
                            humanMap.put(point, cnt+1);
                        }
                        else {
                            if(spaceMap.containsKey(point)) {
                                Integer cnt = spaceMap.get(point);
                                spaceMap.put(point, cnt+1);
                            }
                            else {
                                spaceMap.put(point, 1);
                            }
                        }
                    }
                }
            }

            //humanList 要放这一轮generation之后的新的human了，先clear
            humanList.clear();
            //humanMap中周围有两个或三个的的可以活下去
            for(Map.Entry<Point, Integer> entry : humanMap.entrySet()) {
                if(entry.getValue().equals(2) || entry.getValue().equals(3)) {
                    humanList.add(entry.getKey());
                }
            }
            //空地周围有三个生命体，下一轮就是human了
            for(Map.Entry<Point, Integer> entry : spaceMap.entrySet()) {
                if(entry.getValue().equals(3)) {
                    humanList.add(entry.getKey());
                }
            }
            //System.out.println(humanList.size());

            if(humanList.size() > Max) {
                Max = humanList.size();
                MaxIndex = generations;
            }
        }
        System.out.println(MaxIndex + "-" + Max);
    }
}
