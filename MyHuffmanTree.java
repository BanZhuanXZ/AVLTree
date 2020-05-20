package ds10;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 哈夫曼树java实现
 * 
 * @author 张益达
 *
 */
public class MyHuffmanTree {
	// 封装结点
	public class hfmNode {
		String data;
		double weight;
		hfmNode left;
		hfmNode right;

		public hfmNode(String data, double weight) {
			this.data = data;
			this.weight = weight;
			left = null;
			right = null;
		}
	}

	/**
	 * 创建哈夫曼树
	 * 
	 * @param nodes：结点数组
	 * @return：返回哈夫曼的根结点
	 */
	private hfmNode creatHuffmanTree(List<hfmNode> nodes) {
		while (nodes.size() > 1) {
			// 对结点按照权重升序排列
			quickSort(nodes);
			// 取权重最小的两个森林
			hfmNode left = nodes.get(0);
			hfmNode right = nodes.get(1);
			// 进行合并
			hfmNode par = new hfmNode(null, left.weight + right.weight);
			par.left = left;
			par.right = right;
			// 将left和right从森林中删除，并将par加入到森林集合中
			nodes.remove(0);
			// 这里注意第二次remove（0）时是删除的原数组中的第二个值
			nodes.remove(0);
			nodes.add(par);
		}
		return nodes.get(0);
	}

	public void quickSort(List<hfmNode> nodes) {
		subQuickSort(nodes, 0, nodes.size() - 1);
	}

	/**
	 * 快速排序
	 * 
	 * @param nodes
	 * @param start
	 * @param end
	 */
	public void subQuickSort(List<hfmNode> nodes, int start, int end) {
		if (start > end) {
			return;
		}
		hfmNode baseNode = nodes.get(start);
		double baseValue = baseNode.weight;
		int left = start;
		int right = end;
		while (left < right) {
			while (nodes.get(right).weight >= baseValue && left < right) {
				right--;
			}
			nodes.set(left, nodes.get(right));
			while (nodes.get(left).weight <= baseValue && left < right) {
				left++;
			}
			nodes.set(right, nodes.get(left));
		}
		nodes.set(left, baseNode);
		subQuickSort(nodes, start, left - 1);
		subQuickSort(nodes, left + 1, end);
	}

	/**
	 * 广度遍历
	 * 
	 * @param root：哈夫曼树的根
	 */
	public void BFS(hfmNode root) {
		Queue<hfmNode> queue = new LinkedList<hfmNode>();
		LinkedList<hfmNode> list = new LinkedList<hfmNode>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			hfmNode temp = queue.poll();
			list.offer(temp);
			if (temp.left != null) {
				queue.offer(temp.left);
			}
			if (temp.right != null) {
				queue.offer(temp.right);
			}
		}
		for (hfmNode i : list) {
			System.out.print(i.data + ":" + i.weight + ", ");
		}
	}

	public static void main(String[] args) {
		MyHuffmanTree mht = new MyHuffmanTree();
		LinkedList<hfmNode> list = new LinkedList<hfmNode>();
		list.offer(mht.new hfmNode("C", 1));
		list.offer(mht.new hfmNode("A", 2));
		list.offer(mht.new hfmNode("B", 3));
		list.offer(mht.new hfmNode("D", 5));
		hfmNode root = mht.creatHuffmanTree(list);
		mht.BFS(root);
	}
}
