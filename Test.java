public class Test {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] arr = {1, 3, 5, 8, 9, 2, 6, 7, 6, 8, 9};
        System.out.println(solution.minJumps(arr));
    }

    static class Solution {
        public int minJumps(int[] arr) {
            if (arr[0] == 0) return -1;
            if (arr[0] >= arr.length) return 1;
            int[] memo = new int[arr.length];
            fill(memo, 0, arr, 0);
            return memo[arr.length - 1];
        }

        private void fill(int[] memo, int currIndex, int[] arr, int distance) {
            if (currIndex >= arr.length) return;
            System.out.println("CurrIndex: " + currIndex);
            for (int i = currIndex + 1;  ; i++) {
                if (memo[i] == -1) {
                    memo[i] = 1;
                } else {
                    if (memo[i] > distance) {
                        memo[i] = distance;
                    }
                }
                fill(memo, i, arr, distance + 1);
            }
        }
    }
}
