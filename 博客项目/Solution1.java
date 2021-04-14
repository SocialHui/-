import java.util.Stack;

public class Solution1 {
    //快速排序的非递归写法
    public void sort(int[] array,int left, int right) {
        Stack<Integer> stack = new Stack<>();
        int piv = swap(array,left,right);
        if (piv > left+1) { //代表左边至少有两个数据
            stack.push(left);
            stack.push(piv-1);
        }
        if (piv < right-1) {
            stack.push(piv+1);
            stack.push(right);
        }
        while (!stack.isEmpty()) {
            right = stack.pop();
            left = stack.pop();
            piv = swap(array,left,right);
            if (piv > left+1) { //代表左边至少有两个数据
                stack.push(left);
                stack.push(piv-1);
            }
            if (piv < right-1) {
                stack.push(piv+1);
                stack.push(right);
            }
        }
    }

    //找基准
    public int swap(int[] arr, int left, int right){
        int gap = arr[left];
        while (left < right) {
            while (left < right && arr[right] >= gap) {
                right--;
            }
            arr[left] = arr[right];

            while (left < right && arr[left] <= gap) {
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = gap;
        return right;
    }
}
