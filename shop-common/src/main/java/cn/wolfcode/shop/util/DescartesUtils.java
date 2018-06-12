package cn.wolfcode.shop.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DescartesUtils {

    /**
     * 简单测试： 输入[A,B],[C,D] 输出[A,C],[A,D],[B,C],[B,D]
     */
    public static <T> List<List<T>> descart(List<List<T>> ops) {
        if (ops == null) {
            return null;
        } else {
            DescartWrapper<T> wrapper = new DescartWrapper<T>(ops);
            return wrapper.descart();
        }
    }

    public static class DescartWrapper<T> {
        private List<List<T>> top = new LinkedList<List<T>>();
        private Queue<List<T>> queue = new LinkedList<List<T>>();

        public DescartWrapper(List<List<T>> ops) {
            // ops = [[A, B], [C, D]]
            if (ops.size() > 0) {
                // firstNode = [[A, B]]
                List<T> firstNode = ops.get(0);
                for (T item : firstNode) {
                    List<T> temp = new LinkedList<T>();
                    temp.add(item);
                    top.add(temp);
                    // top = [[A], [B]]
                }
                if (ops.size() > 1) {
                    for (int i = 1; i < ops.size(); i++) {
                        // queue = [[C, D]]
                        queue.add(ops.get(i));
                    }
                }
            }
        }

        /**
         * 执行笛卡尔
         */
        public List<List<T>> descart() {
            // 获取待笛卡尔队列中的第一个列表；
            List<T> fi = queue.poll();
            // fi = [C, D]
            while (fi != null) {
                // 创建一个容器临时装拷贝的元素
                List<List<T>> copyContainer = new LinkedList<List<T>>();
                // 让top中的元素自我复制size-1份（深拷贝）；
                for (T t : fi) {
                    // t = C, t = D
                    // top.size = 2
                    for (int j = 0; j < top.size(); j++) {
                        List<T> copy = new LinkedList<T>(top.get(j));
                        // copy = [A]
                        copy.add(t);
                        // copy = [A, C], copy = [B, C]
                        copyContainer.add(copy);
                        // copyContainer = [[A, C], [B, C]]
                    }

                }
                top = copyContainer;
                fi = queue.poll();
            }
            return top;
        }
    }
}
