package ds10;

import java.util.ArrayList;

public class Heap {
	// 存储数据的数组，堆
	public ArrayList<hNode> nodelist;
	// 堆的大小
	private int length;

	public Heap() {
	}

	public Heap(ArrayList<hNode> nodelist) {
		this.nodelist = nodelist;
		this.length = nodelist.size();
	}

	// 交换函数，交换两个节点的位置
	public void swap(hNode nodeA, hNode nodeB) {
		hNode nodeTemp = new hNode(nodeA.key, nodeA.value);
		nodeA.key = nodeB.key;
		nodeA.value = nodeB.value;
		nodeB.key = nodeTemp.key;
		nodeB.value = nodeTemp.value;
	}

	// 堆的长度
	public int length() {
		return length;
	}

	// 判断是不是叶节点
	public boolean isLeaf(int pos) {
		return (pos >= length / 2) && (pos < length);
	}

	// 返回左子节点的地址
	public int leftChild(int pos) {
		return 2 * pos + 1;
	}

	// 返回右子节点的地址
	public int rightChild(int pos) {
		return 2 * pos + 2;
	}

	// 返回父节点的地址
	public int parent(int pos) {
		return (pos - 1) / 2;
	}

	// 建堆函数
	public void buildHeap() {
		// 从最后一个非叶节点开始逐层向上遍历进行下沉操作
		for (int i = length / 2 - 1; i >= 0; i--) {
			shiftDown(i);
		}
	}

	// 下沉函数
	public void shiftDown(int pos) {
		// 知道下沉到叶节点
		while (!isLeaf(pos)) {
			int left = leftChild(pos);
			int right = rightChild(pos);
			// 获取左右子节点值较小的节点键
			if (right < length && nodelist.get(right).key > nodelist.get(left).key) {
				left = right;
			}
			// 如果子节点的值大于父节点的值就交换位置，并将子节点的键赋给父节点
			if (nodelist.get(left).key > nodelist.get(pos).key) {
				swap(nodelist.get(left), nodelist.get(pos));
				pos = left;
			} else {
				return;
			}
		}
	}

	// 插入新节点
	public void insert(hNode node) {
		// 将新节点放在链表尾
		nodelist.add(node);
		int curr = nodelist.size() - 1;
		// 上拉
		while ((curr != 0) && nodelist.get(curr).key > nodelist.get(parent(curr)).key) {
			swap(nodelist.get(curr), nodelist.get(parent(curr)));
			curr = parent(curr);
		}
		// 堆长度加一
		length++;
	}

	// 取最大值
	public hNode removeFirst() {
		if (length <= 0) {
			System.out.println("Heap is empty");
			return null;
		}
		// 将最后一个元素和堆顶交换位置,并将堆大小减一
		swap(nodelist.get(0), nodelist.get(--length));
		if (length != 0) {
			shiftDown(0);
		}
		return nodelist.get(length);
	}

	public static void main(String[] args) {
		ArrayList<hNode> nodelist = new ArrayList<hNode>();
		nodelist.add(new hNode(1, "A"));
		nodelist.add(new hNode(2, "B"));
		nodelist.add(new hNode(10, "C"));
		nodelist.add(new hNode(5, "D"));

		Heap heap = new Heap(nodelist);

		System.out.println("当前数组结构");
		for (int i = 0; i < heap.length; i++) {
			System.out.println(heap.nodelist.get(i).key + " : " + heap.nodelist.get(i).value);
		}
		heap.buildHeap();
		System.out.println("构建堆结构后");
		for (int i = 0; i < heap.length; i++) {
			System.out.println(heap.nodelist.get(i).key + " : " + heap.nodelist.get(i).value);
		}

	}
}
