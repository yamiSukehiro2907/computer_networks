public class Main {
    public static void main(String[] args) {
        int[] arr = { 1, 2, 3, 4, 5 };
        Node head = create(arr);
        int k = 5;
        System.out.println("Starting with Node: ");
        print(head);
        System.out.println("k: " + k);
        print((new Solution()).swapKth(head, k));
    }

    static class Node {
        Node next;
        int val;

        Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    static void print(Node head) {
        while (head != null) {
            System.out.print(head.val);
            if (head.next != null) {
                System.out.print(" -> ");
            }
            head = head.next;
        }
        System.out.println();
    }

    static Node create(int[] arr) {
        Node head = new Node(arr[0], null);
        Node temp = head;
        for (int i = 1; i < arr.length; i++) {
            temp.next = new Node(arr[i], null);
            temp = temp.next;
        }
        return head;
    }

    static class Solution {
        public Node swapKth(Node head, int k) {
            if (head == null || head.next == null)
                return head;
            int length = findLength(head);
            System.out.println("Length : " + length);
            Node leftLeft = findNode(head, k - 1);
            Node rightLeft = findNode(head, length - k);
            if (k == 1)
                return swap(head, rightLeft);
            else if (k == length)
                return swap(head, leftLeft);
            else if (length % 2 == 1 && k == (length + 1) / 2)
                return head;
            else if (leftLeft.next != null && leftLeft.next == rightLeft)
                swap2(leftLeft, rightLeft);
            else if (rightLeft.next != null && rightLeft.next == leftLeft)
                swap2(rightLeft, leftLeft);
            else {
                System.out.println("Nodes:" + " n1Left: " + leftLeft.val + " , n2Left: " + rightLeft.val);
                Node n1 = leftLeft.next;
                Node n2 = rightLeft.next;
                leftLeft.next = n2;
                rightLeft.next = n1;
                Node temp = n1.next;
                n1.next = n2.next;
                n2.next = temp;
            }
            return head;
        }

        private void swap2(Node n1Left, Node n2Left) {
            System.out.println("Nodes:" + " n1Left: " + n1Left.val + " , n2Left: " + n2Left.val);
            Node n1 = n1Left.next;
            Node n2 = n2Left.next;
            n1.next = n2.next;
            n1Left.next = n2;
            n2.next = n1;
        }

        private Node findNode(Node head, int length) {
            Node temp = head;
            while (length > 1 && temp.next != null) {
                length--;
                temp = temp.next;
            }
            return temp;
        }

        private Node swap(Node n1, Node n2Prev) {
            System.out.println("Nodes:" + " n1Left: " + n1.val + " , n2Left: " + n2Prev.val);
            Node n2 = n2Prev.next;
            n2.next = n1.next;
            n2Prev.next = n1;
            n1.next = null;
            return n2;
        }

        private int findLength(Node head) {
            if (head == null)
                return 0;
            Node temp = head;
            int count = 0;
            while (temp != null) {
                temp = temp.next;
                count++;
            }
            return count;
        }

    }
}