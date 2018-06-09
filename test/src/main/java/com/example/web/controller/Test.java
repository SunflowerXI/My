package com.example.web.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Test {

	public static void main(String[] args) {

		List<Integer> array = new Test().random();
		Test binTree = new Test();
		binTree.createBinTree();
		// nodeList中第0个索引处的值即为根节点
		Node root = nodeList.get(0);

		System.out.println("先序遍历：");
		preOrderTraverse(root);
		System.out.println();

		System.out.println("中序遍历：");
		inOrderTraverse(root);
		System.out.println();

		System.out.println("后序遍历：");
		postOrderTraverse(root);
	}

	public List<Integer> random() {
		List<Integer> list = new ArrayList<Integer>();
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			list.add(random.nextInt(1000));
		}
		return list;
	}

	/**
	 * Java二叉树遍历
	 * 
	 * 功能：把一个数组的值存入二叉树中，然后进行3种方式的遍历
	 * 
	 * 参考资料0:数据结构(C语言版)严蔚敏
	 * 
	 * 参考资料1：http://zhidao.baidu.com/question/81938912.html
	 * 
	 * 参考资料2：http://cslibrary.stanford.edu/110/BinaryTrees.html#java
	 * 
	 * @packge com.boonya.utils.algorithm.BinaryTree
	 * @date 2016年5月19日 下午2:50:04
	 * @authorocaicai@yeah.net @date: 2011-5-17
	 * @comment
	 * @update
	 */

	private int[] array = {2,3,1,4,5};

	private static List<Node> nodeList = null;

	/**
	 * 内部类：节点
	 * 
	 * @author ocaicai@yeah.net @date: 2011-5-17
	 * 
	 */
	private static class Node {
		Node leftChild;
		Node rightChild;
		int data;

		Node(int newData) {
			leftChild = null;
			rightChild = null;
			data = newData;
		}
	}

	/**
	 * 公开内部类：节点
	 * 
	 * @author ocaicai@yeah.net @date: 2011-5-17
	 * 
	 */
	public static class NodeObject {
		NodeObject leftChild;
		NodeObject rightChild;
		Object data;

		NodeObject(Object newData) {
			leftChild = null;
			rightChild = null;
			data = newData;
		}

		public NodeObject getLeftChild() {
			return leftChild;
		}

		public void setLeftChild(NodeObject leftChild) {
			this.leftChild = leftChild;
		}

		public NodeObject getRightChild() {
			return rightChild;
		}

		public void setRightChild(NodeObject rightChild) {
			this.rightChild = rightChild;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

	}

	/**
	 * 创建Integer数组二叉树
	 * 
	 * @MethodName: createBinTree @Description: @throws
	 */
	public void createBinTree() {
		nodeList = new LinkedList<Node>();
		// 将一个数组的值依次转换为Node节点
		for (int nodeIndex = 0; nodeIndex < array.length; nodeIndex++) {
			nodeList.add(new Node(array[nodeIndex]));
		}
		// 对前lastParentIndex-1个父节点按照父节点与孩子节点的数字关系建立二叉树
		for (int parentIndex = 0; parentIndex < array.length / 2 - 1; parentIndex++) {
			// 左孩子
			nodeList.get(parentIndex).leftChild = nodeList.get(parentIndex * 2 + 1);
			// 右孩子
			nodeList.get(parentIndex).rightChild = nodeList.get(parentIndex * 2 + 2);
		}
		// 最后一个父节点:因为最后一个父节点可能没有右孩子，所以单独拿出来处理
		int lastParentIndex = array.length / 2 - 1;
		// 左孩子
		nodeList.get(lastParentIndex).leftChild = nodeList.get(lastParentIndex * 2 + 1);
		// 右孩子,如果数组的长度为奇数才建立右孩子
		if (array.length % 2 == 1) {
			nodeList.get(lastParentIndex).rightChild = nodeList.get(lastParentIndex * 2 + 2);
		}
	}

	/**
	 * 创建Object数组二叉树
	 * 
	 * @MethodName: createBinTree @Description: @throws
	 */
	public void createBinTree(LinkedList<NodeObject> nodeList) {
		// 对前lastParentIndex-1个父节点按照父节点与孩子节点的数字关系建立二叉树
		for (int parentIndex = 0; parentIndex < array.length / 2 - 1; parentIndex++) {
			// 左孩子
			nodeList.get(parentIndex).leftChild = nodeList.get(parentIndex * 2 + 1);
			// 右孩子
			nodeList.get(parentIndex).rightChild = nodeList.get(parentIndex * 2 + 2);
		}
		// 最后一个父节点:因为最后一个父节点可能没有右孩子，所以单独拿出来处理
		int lastParentIndex = array.length / 2 - 1;
		// 左孩子
		nodeList.get(lastParentIndex).leftChild = nodeList.get(lastParentIndex * 2 + 1);
		// 右孩子,如果数组的长度为奇数才建立右孩子
		if (array.length % 2 == 1) {
			nodeList.get(lastParentIndex).rightChild = nodeList.get(lastParentIndex * 2 + 2);
		}
	}

	/**
	 * 先序遍历
	 * 
	 * 这三种不同的遍历结构都是一样的，只是先后顺序不一样而已
	 * 
	 * @param node
	 *            遍历的节点
	 */
	public static void preOrderTraverse(Node node) {
		if (node == null)
			return;
		System.out.print(node.data + " ");
		preOrderTraverse(node.leftChild);
		preOrderTraverse(node.rightChild);
	}

	/**
	 * 中序遍历
	 * 
	 * 这三种不同的遍历结构都是一样的，只是先后顺序不一样而已
	 * 
	 * @param node
	 *            遍历的节点
	 */
	public static void inOrderTraverse(Node node) {
		if (node == null)
			return;
		inOrderTraverse(node.leftChild);
		System.out.print(node.data + " ");
		inOrderTraverse(node.rightChild);
	}

	/**
	 * 后序遍历
	 * 
	 * 这三种不同的遍历结构都是一样的，只是先后顺序不一样而已
	 * 
	 * @param node
	 *            遍历的节点
	 */
	public static void postOrderTraverse(Node node) {
		if (node == null)
			return;
		postOrderTraverse(node.leftChild);
		postOrderTraverse(node.rightChild);
		System.out.print(node.data + " ");
	}

	/**
	 * 先序遍历
	 * 
	 * 这三种不同的遍历结构都是一样的，只是先后顺序不一样而已
	 * 
	 * @param node
	 *            遍历的节点
	 */
	public static void preOrderTraverseObj(NodeObject node) {
		if (node == null)
			return;
		System.out.print(node.data + " ");
		preOrderTraverseObj(node.leftChild);
		preOrderTraverseObj(node.rightChild);
	}

	/**
	 * 中序遍历
	 * 
	 * 这三种不同的遍历结构都是一样的，只是先后顺序不一样而已
	 * 
	 * @param node
	 *            遍历的节点
	 */
	public static void inOrderTraverseObj(NodeObject node) {
		if (node == null)
			return;
		inOrderTraverseObj(node.leftChild);
		System.out.print(node.data + " ");
		inOrderTraverseObj(node.rightChild);
	}

	/**
	 * 后序遍历
	 * 
	 * 这三种不同的遍历结构都是一样的，只是先后顺序不一样而已
	 * 
	 * @param node
	 *            遍历的节点
	 */
	public static void postOrderTraverseObj(NodeObject node) {
		if (node == null)
			return;
		postOrderTraverseObj(node.leftChild);
		postOrderTraverseObj(node.rightChild);
		System.out.print(node.data + " ");
	}

}
