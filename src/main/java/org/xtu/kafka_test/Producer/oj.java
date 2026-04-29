package org.xtu.kafka_test.Producer;

import java.util.*;
import java.io.*;
public class oj {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        //多组输入，最多20组；
        while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
            int n = Integer.parseInt(line.trim());
            String[] parts = br.readLine().trim().split(" ");
            long[] arr = new long[3 * n];
            for (int i = 0; i < 3 * n; i++) {
                arr[i] = Long.parseLong(parts[i]);
            }
            //从小到大排序；
            Arrays.sort(arr);
            long ans = 0;
            int l = 0;
            int r = 3 * n - 1;
            //一共n组
            for (int i = 0; i < n; i++) {
                long a = arr[l++];
                long b = arr[r - 1];
                long c = arr[r];
                //计算本组不和谐度
                ans += (a - b) * (a - b) + (b - c) * (b - c);
                r -= 2;
            }
            System.out.println(ans);
        }
    }
}