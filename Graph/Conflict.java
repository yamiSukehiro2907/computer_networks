package Graph;

public class Conflict {
    public static void main(String[] args) {
        char[][] area = {
                { 'U', 'R', 'U' },
                { 'R', 'U', 'R' },
                { 'R', 'R', 'U' }
        };
        Solution solution = new Solution();
        area = solution.find(area);
        for (char[] chars : area) {
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println();
        }
    }

    static class Solution {
        private char[][] matrix;
        private int row;
        private int col;
        private boolean[][] visited;

        public char[][] find(char[][] matrix) {
            this.matrix = matrix;
            this.row = matrix.length;
            this.col = matrix[0].length;
            this.visited = new boolean[row][col];
            for (int i = 0; i < row; i++) {
                if (matrix[i][0] == 'U' && !visited[i][0]) {
                    dfs(i, 0);
                }
            }
            for (int i = 0; i < row; i++) {
                if (matrix[i][col - 1] == 'U' && !visited[i][col - 1]) {
                    dfs(i, col - 1);
                }
            }
            for (int j = 0; j < col; j++) {
                if (matrix[0][j] == 'U' && !visited[0][j]) {
                    dfs(0, j);
                }
            }
            for (int j = 0; j < col; j++) {
                if (matrix[row - 1][j] == 'U' && !visited[row - 1][j]) {
                    dfs(row - 1, j);
                }
            }
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (!visited[i][j]) {
                        matrix[i][j] = 'R';
                    }
                }
            }
            return matrix;
        }

        private void dfs(int r, int c) {
            visited[r][c] = true;
            if (r - 1 >= 0 && matrix[r - 1][c] == 'U' && !visited[r - 1][c]) {
                dfs(r - 1, c);
            }
            if (c - 1 >= 0 && matrix[r][c - 1] == 'U' && !visited[r][c - 1]) {
                dfs(r, c - 1);
            }
            if (c + 1 < col && matrix[r][c + 1] == 'U' && !visited[r][c + 1]) {
                dfs(r, c + 1);
            }
            if (r + 1 < row && matrix[r + 1][c] == 'U' && !visited[r + 1][c]) {
                dfs(r + 1, c);
            }
        }
    }
}