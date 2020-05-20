package ds10;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 实现平衡查找树
 * 
 * @author 张益达
 *
 */
public class AVLTree {
	// 封装结点
	public class AVLNode {
		public AVLNode parent;
		public AVLNode lChild;
		public AVLNode rChild;
		public int data;

		public AVLNode(AVLNode parent, AVLNode lChild, AVLNode rChild, int data) {
			this.parent = parent;
			this.lChild = lChild;
			this.rChild = rChild;
			this.data = data;
		}

		public AVLNode(int data) {
			this(null, null, null, data);
		}

		public AVLNode(AVLNode parent, int data) {
			this(parent, null, null, data);
		}
	}

	private AVLNode root;
	private final int LEFT = 1;
	private final int RIGHT = -1;
	private final int MAX_LEFT = 2;
	private final int MAX_RIGHT = -2;

	public AVLTree() {
		root = null;
	}

	public AVLNode getRoot() {
		return root;
	}

	public void put(int data) {
		putData(root, data);
	}

	/**
	 * 添加结点
	 * 
	 * @param node：待插入结点树的根
	 * @param data：插结点的值
	 * @return：成功返回true，失败false
	 */

	private boolean putData(AVLNode node, int data) {
		// 树为空
		if (node == null) {
			node = new AVLNode(data);
			root = node;
			return true;
		}

		AVLNode temp = node;
		AVLNode par;
		int i;
		do {
			i = data - temp.data;
			par = temp;
			if (i < 0) {
				temp = temp.lChild;
			} else if (i > 0) {
				temp = temp.rChild;
			} else {
				return false;
			}
		} while (temp != null);

		if (i < 0) {
			par.lChild = new AVLNode(par, data);
		} else if (i > 0) {
			par.rChild = new AVLNode(par, data);
		}
		balance(par);
		return true;
	}

	/**
	 * 重新平衡树
	 * 
	 * @param node
	 * @param data插入的值
	 */
	public void balance(AVLNode node) {
		// 根结点的parent为null
		while (node != null) {
			// node的左子树值较大，包含LL，LR情况
			if (balanceValue(node) == MAX_LEFT) {
				fixTree(node, LEFT);
				// node的右子树值较大，包含RL，RR情况
			} else if (balanceValue(node) == MAX_RIGHT) {
				fixTree(node, RIGHT);
			}
			node = node.parent;
		}
	}

	/**
	 * 求结点的平衡值，其实就是调用高度差方法
	 * 
	 * @param node
	 * @return返回平衡值
	 */
	public int balanceValue(AVLNode node) {
		if (node != null) {
			return getHeightDiffer(node);
		} else {
			return 0;
		}
	}

	/**
	 * 求结点的左右子树的高度差
	 * 
	 * @param node
	 * @return返回高度差
	 */
	public int getHeightDiffer(AVLNode node) {
		if (node == null) {
			return 0;
		} else {
			return getHeight(node.lChild) - getHeight(node.rChild);
		}
	}

	/**
	 * 求结点的高度
	 * 
	 * @param node
	 * @return返回结点的高度
	 */
	public int getHeight(AVLNode node) {
		if (node == null) {
			return 0;
		} else {
			// 每次返回高度+1，分别对node的左右子树求高度，取其中的最大值
			return 1 + Math.max(getHeight(node.lChild), getHeight(node.rChild));
		}
	}

	/**
	 * 调整树到平衡
	 * 
	 * @param node从下到上第一个失衡的结点
	 * @param type指示node的左子树还是右子树失衡
	 * @param data插入的值
	 */
	public void fixTree(AVLNode node, int type) {
		if (type == LEFT) {
			AVLNode lc = node.lChild;
			// 如果lc的左子树大于右子树说明是LL型
			if (getHeightDiffer(lc) > 0) {
				rightRotation(node);
			} else if (getHeightDiffer(lc) < 0) {
				leftRotation(lc);
				rightRotation(node);
			}
		} else if (type == RIGHT) {
			AVLNode rc = node.rChild;
			// 如果rc的右子树高，说明为RR
			if (getHeightDiffer(rc) < 0) {
				leftRotation(node);
			} else if (getHeightDiffer(rc) > 0) {
				rightRotation(rc);
				leftRotation(node);
			}
		} else {
			System.out.println("type参数输入不正确！");
		}
	}

	/**
	 * 左旋
	 * 
	 * @param node
	 * @return返回左旋后代替原来node结点的新结点
	 */
	public AVLNode leftRotation(AVLNode node) {
		if (node != null) {
			AVLNode rc = node.rChild;
			AVLNode par = node.parent;
			// 链接rc.lChid和node（右孩子）
			node.rChild = rc.lChild;
			if (rc.lChild != null) {
				rc.lChild.parent = node;
			}
			// 链接node.parent和rc
			rc.parent = node.parent;
			if (par != null) {
				if (par.rChild == node) {
					par.rChild = rc;
				} else if (par.lChild == node) {
					par.lChild = rc;
				}
				// 更新根结点
			} else {
				this.root = rc;
			}
			// 链接node结点和rc结点（左孩子）
			rc.lChild = node;
			node.parent = rc;
			return rc;
		} else {
			return null;
		}
	}

	/**
	 * 右旋
	 * 
	 * @param node
	 * @return返回右旋后代替原来node结点的新结点
	 */
	public AVLNode rightRotation(AVLNode node) {
		AVLNode lc = node.lChild;
		AVLNode par = node.parent;

		// 链接lc.rChild和node结点
		node.lChild = lc.rChild;
		if (lc.rChild != null) {
			lc.rChild.parent = node;
		}

		// 链接par和lc
		lc.parent = node.parent;
		if (par != null) {
			if (par.rChild == node) {
				par.rChild = lc;
			} else if (par.lChild == node) {
				par.lChild = lc;
			}
		} else {
			this.root = lc;
		}

		// 链接node和lc（右孩子）
		lc.rChild = node;
		node.parent = lc;
		return lc;
	}

	/**
	 * 删除节点
	 * 
	 * @param value
	 * @return
	 */
	public AVLNode deleteNode(int value) {
		AVLNode node = getNode(value);

		if (node == null) {
			System.out.println("该节点不存在树中！");
			return null;
		}

		AVLNode par = node.parent;
		AVLNode lc = node.lChild;
		AVLNode rc = node.rChild;
		// 检查的结点，默认是par
		AVLNode checkNode = par;

		// 1.删除的结点为叶节点（只有一个结点的树就是根结点）
		if (lc == null && rc == null) {
			// 删除的为根结点
			if (par == null) {
				this.root = null;
				// 删除的为叶结点
			} else {
				if (par.rChild == node) {
					par.rChild = null;
				} else if (par.lChild == node) {
					par.lChild = null;
				}

			}
			// 2.删除的结点左右都不为空(此时node为更新后的结点)
		} else if (lc != null && rc != null) {
			AVLNode successor = getSuccessor(node);
			int temp = successor.data;
			deleteNode(temp);
			node.data = temp;
			// 更新checkNode
			checkNode = successor;
			// 3.删除的结点左孩子不为空，右孩子为空
		} else if (lc != null && rc == null) {
			if (par != null) {
				if (par.rChild == node) {
					par.rChild = node.lChild;
					node.lChild.parent = par;
				} else if (par.lChild == node) {
					par.lChild = node.lChild;
					node.lChild.parent = par;
				}
			} else {
				// node为根结点，不需要检查平衡性
				this.root = lc;
				lc.parent = null;
			}
			// 4.删除的结点右孩子不为空，左孩子为空
		} else if (lc == null && rc != null) {
			if (par != null) {
				if (par.rChild == node) {
					par.rChild = node.rChild;
					node.rChild.parent = par;
				} else if (par.lChild == node) {
					par.lChild = node.rChild;
					node.rChild.parent = par;
				}
			} else {
				// node为根结点,不需要检查平衡性
				this.root = rc;
				rc.parent = null;
			}
		}
		// 检查平衡性
		balance(checkNode);
		return node;
	}

	/**
	 * 根据提供的值获取相应的结点，如果树中不存在返回null
	 * 
	 * @param value
	 * @return
	 */
	public AVLNode getNode(int value) {
		AVLNode node = this.root;
		while (node != null) {
			if (value == node.data) {
				break;
			} else if (value < node.data) {
				node = node.lChild;
			} else {
				node = node.rChild;
			}
		}
		return node;
	}

	/**
	 * 获取node 结点的后继结点
	 * 
	 * @param node
	 * @return
	 */
	public AVLNode getSuccessor(AVLNode node) {
		// 1.node结点有右子树，后继节点为右子树中最小值
		if (node.rChild != null) {
			AVLNode par = node.rChild;
			AVLNode temp = par.lChild;

			while (temp != null) {
				temp = temp.lChild;
				par = par.lChild;
			}
			return par;
			// 2.若node结点右子树为空，则后继结点在其祖先中，这个祖先结点使其父节点的左孩子
		} else {
			AVLNode par = node.parent;
			if (par == null) {
				System.out.println("此结点无后继节点！");
				return null;
			} else {
				AVLNode grantpar = par.parent;
				// 不为空且是其父节点的右孩子时，继续向上查找（出循环的条件为grantpar要么为null，要么par使其父节点的左孩子
				while (grantpar != null && par != grantpar.lChild) {
					par = par.parent;
					grantpar = grantpar.parent;
				}
				if (grantpar == null) {
					return null;
				} else {
					return par;
				}
			}
		}
	}

	/**
	 * 中序遍历
	 * 
	 * @param node
	 */
	public void inOrder(AVLNode node) {
		if (node == null) {
			return;
		}
		inOrder(node.lChild);
		System.out.print(node.data + ",");
		inOrder(node.rChild);
	}

	public void BSF(AVLNode node) {
		Queue<AVLNode> que = new LinkedList<AVLNode>();
		LinkedList<AVLNode> list = new LinkedList<AVLNode>();
		que.offer(node);
		while (!que.isEmpty()) {
			AVLNode temp = que.poll();
			list.add(temp);
			if (temp.lChild != null) {
				que.offer(temp.lChild);
			}
			if (temp.rChild != null) {
				que.offer(temp.rChild);
			}
		}
		for (AVLNode i : list) {
			System.out.print(i.data + ",");
		}
	}

	public static void main(String[] args) {
		AVLTree avlt = new AVLTree();
		avlt.put(20);
		avlt.put(10);
		avlt.put(15);
		avlt.put(30);
		avlt.put(25);
		avlt.put(8);
		avlt.put(26);

//		avlt.deleteNode(20);
		avlt.deleteNode(20);

		System.out.print("中序遍历：");
		avlt.inOrder(avlt.getRoot());
		System.out.println();
		System.out.print("层序遍历：");
		avlt.BSF(avlt.getRoot());
	}
}
