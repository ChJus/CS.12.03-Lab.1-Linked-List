package impl;

import common.InvalidIndexException;
import common.InvalidListException;
import common.ListNode;
import interfaces.IFilterCondition;
import interfaces.IListManipulator;
import interfaces.IMapTransformation;
import interfaces.IReduceOperator;

import java.util.ArrayList;

/**
 * This class represents the iterative implementation of the IListManipulator interface.
 */
public class ListManipulator implements IListManipulator {
  // Returns the size of the Linked List given its head/start node
  @Override
  public int size(ListNode head) {
    if (isEmpty(head)) return 0; // If the node is non-existent, return 0

    // Assign a pointer to the head node as 'current node'
    ListNode currentNode = head;
    int count = 1; // Count the head node

    // Loop through the linked list while the next node is not the head node
    // i.e., we are not at the tail/end node yet.
    while (currentNode.next != head) {
      currentNode = currentNode.next;
      count++;
    }
    return count;
  }

  // Returns whether Linked List is empty (head is null)
  @Override
  public boolean isEmpty(ListNode head) {
    return head == null;
  }

  // Returns whether list contains element
  @Override
  public boolean contains(ListNode head, Object element) {
    if (isEmpty(head)) return false; // If list is empty, does not contain element

    // Check if first node is element, as loop does not consider
    if (head.element.equals(element)) return true;

    // Iterate through nodes and check if their value is equal to element
    ListNode currentNode = head;
    while (currentNode.next != head) {
      currentNode = currentNode.next;
      if (currentNode.element.equals(element)) return true;
    }
    return false;
  }

  // Returns number of element occurrences in a linked list
  @Override
  public int count(ListNode head, Object element) {
    if (isEmpty(head) || element == null) return 0; // no occurrences if no item specified or list is empty

    // First node is not considered in loop
    ListNode currentNode = head;
    int count = head.element.equals(element) ? 1 : 0;

    // Increment count if it is an occurrence of element
    while (currentNode.next != head) {
      currentNode = currentNode.next;
      if (currentNode.element.equals(element)) count++;
    }
    return count;
  }

  // Prints linked list; equivalent to Arrays.toString() method.
  @Override
  public String convertToString(ListNode head) {
    if (isEmpty(head)) return ""; // Send empty if linked list is empty

    // Print each element with ',' delimiter
    ListNode currentNode = head;
    String result = currentNode.element + "";
    while (currentNode.next != head) {
      currentNode = currentNode.next;
      result += "," + currentNode.element;
    }

    return result;
  }

  // Get nth element from the front, with the first element being the 0th element
  @Override
  public Object getFromFront(ListNode head, int n) throws InvalidIndexException {
    if (n < 0 || isEmpty(head)) throw new InvalidIndexException();

    ListNode currentNode = head;
    while (currentNode.next != head && n > 0) {
      currentNode = currentNode.next;
      n--;
    }

    // If list has less than n elements, throw exception
    // Otherwise, return current element (which points to nth element)
    if (n != 0) throw new InvalidIndexException();
    return currentNode.element;
  }

  // Get nth element from back, achieved using getFromFront() method
  @Override
  public Object getFromBack(ListNode head, int n) throws InvalidIndexException {
    return getFromFront(head, size(head) - n - 1);
  }

  // Returns whether two lists are identical (same elements in same order)
  @Override
  public boolean equals(ListNode firstNode, ListNode secondNode) {
    // Return true if both lists are empty;
    //        false if lists have different size;
    if (firstNode == null && secondNode == null) return true;
    if (size(firstNode) != size(secondNode)) return false;

    // Check if nth element in list are equal
    // This implementation may be slightly less computationally efficient as it
    // uses the getFromFront() method.
    try {
      for (int i = 0; i < size(firstNode); i++) {
        if (!getFromFront(firstNode, i).equals(getFromFront(secondNode, i))) {
          return false;
        }
      }
    } catch (InvalidIndexException e) {
      // Should never throw exception as index is constrained to valid range
      return false;
    }

    return true;
  }

  // Returns whether list contains duplicates
  @Override
  public boolean containsDuplicates(ListNode head) {
    // If the list is empty, then there are no duplicates.
    if (head == null) return false;

    // Store a list of previous items in ArrayList
    // Return true if an element has already been added, and it occurs again in list
    ArrayList<Object> uniqueItems = new ArrayList<>();
    ListNode currentNode = head;
    do {
      if (uniqueItems.contains(currentNode.element)) return true;
      uniqueItems.add(currentNode.element);
      currentNode = currentNode.next;
    } while (currentNode != head);

    return false;
  }

  // Add node to start of linked list
  @Override
  public ListNode addHead(ListNode head, ListNode node) {
    // Assign tail pointer to last node
    ListNode tail = head.previous;
    tail.next = node;     // Modify tail's nextNode pointer, assign to new head node
    node.previous = tail; // Make new head node's previous node pointer assigned to tail
    node.next = head;     // Make the original head the node after the new start node
    head.previous = node; // Assign head's previous pointer to new head node.
    return node;          // Return pointer to new head node
  }

  // Appends list to another
  @Override
  public ListNode append(ListNode firstListHead, ListNode secondListHead) {
    // If both are empty, return null;
    // Otherwise, if one is empty, simply return the other non-empty list
    if (firstListHead == null && secondListHead == null) return null;
    if (firstListHead == null) return secondListHead;
    if (secondListHead == null) return firstListHead;

    // Make first list's tail node's nextNode point to second list's head;
    // make second list's last node's nextNode point to first list's head;
    // make second list's head node's previousNode point to first list's tail node;
    // make first list's head node's previousNode point to second list's tail node.
    ListNode firstTail = firstListHead.previous;
    ListNode secondTail = secondListHead.previous;
    firstTail.next = secondListHead;
    secondTail.next = firstListHead;
    secondListHead.previous = firstTail;
    firstListHead.previous = secondTail;

    return firstListHead; // Return pointer to first node
  }

  // Inserts a node to index n in the linked list
  public ListNode insert(ListNode head, ListNode node, int n) throws InvalidIndexException {
    // Reject invalid index (negative or larger than list size)
    // If n = 0, simply call addHead, if n == size of list, then simply call append
    if (n < 0 || n > size(head)) throw new InvalidIndexException();
    if (n == 0) return addHead(head, node);
    if (n == size(head)) return append(head, node);

    // Otherwise, find the node before the index position we are inserting new node to
    ListNode currentNode = head;
    while (n != 1) {
      currentNode = currentNode.next;
      n--;
    }

    // Modify the next and previous pointers to add node between currentNode and currentNode.next
    node.next = currentNode.next;
    node.previous = currentNode;
    currentNode.next = node;

    return head;
  }

  // Deletes the first occurrence of an element from the list
  public ListNode delete(ListNode head, Object elem) {
    // If the list only has a single node that is the element to be removed, return null.
    // If the list does not have the specified element, return the unchanged array
    if (head.element.equals(elem) && size(head) == 1) return null;
    if (!contains(head, elem)) return head;

    // Otherwise, find node element to be deleted
    ListNode currentNode = head;
    while (!currentNode.element.equals(elem)) {
      currentNode = currentNode.next;
    }

    // If removing head node, make second node new head and modify points for
    // the tail node and new head node.
    if (currentNode.equals(head)) {
      ListNode tail = head.previous;
      ListNode newHead = head.next;
      newHead.previous = tail;
      tail.next = newHead;
      return newHead;
    } else {
      // Otherwise, simply reassign pointers of adjacent nodes to point to one another
      // instead of to the node to be removed.
      currentNode.previous.next = currentNode.next;
      currentNode.next.previous = currentNode.previous;
    }

    return head;
  }

  // Reverses a list
  @Override
  public ListNode reverse(ListNode head) {
    // If list is empty or has length of 1, simply return unmodified list.
    if (isEmpty(head) || size(head) == 1) return head;

    // Make new head the tail node
    ListNode newHead = head.previous;
    ListNode currentNode = head;
    do {
      // Swap the pointer addresses of the next and previous fields of the current node,
      // then move to previous node of current node (which is now assigned to currentNode.next)
      ListNode temp = currentNode.next;
      currentNode.next = currentNode.previous;
      currentNode.previous = temp;
      currentNode = currentNode.next;
    } while (currentNode != head);

    return newHead;
  }

  // Split a list into two, given a delimiter node.
  // Note this delimiter node is placed in the second part of the list after splitting.
  @Override
  public ListNode split(ListNode head, ListNode node) throws InvalidListException {
    // If the head node or delimiter node is null, or head node
    // is the delimiter node, throw invalid argument exception.
    if (isEmpty(head) || node == null ||
        head.element.equals(node.element)) throw new InvalidListException();

    // Otherwise, find the delimiter node from the list,
    // and make pointer reassignments so the list is split into two circular doubly linked lists
    ListNode currentNode = head;
    do {
      if (currentNode.element.equals(node.element)) {
        ListNode tail = head.previous;
        currentNode.previous.next = head;
        head.previous = currentNode.previous;
        currentNode.previous = tail;
        tail.next = currentNode;

        // Store the two separate circular doubly linked lists as a 'linked list' result
        // with two nodes, respectively representing the first and second resultant lists.
        ListNode result = new ListNode(head);
        result.next = new ListNode(currentNode);
        result.previous = result.next;
        result.next.next = result;
        result.next.previous = result;

        return result;
      }
      currentNode = currentNode.next;
    } while (currentNode != head);

    return head;
  }

  // Perform mapping transformation on list. Essentially, loops through the list and modifies
  // each element using the provided transformation function.
  // Note to self: this is a nice example of dependency injection, in which we use a provided
  // argument to perform transformations, allowing decoupling of transformation logic into
  // a separate class. Good OOP practice :).
  @Override
  public ListNode map(ListNode head, IMapTransformation transformation) {
    if (isEmpty(head)) return null;

    ListNode currentNode = head;
    do {
      currentNode.element = transformation.transform(currentNode.element);
      currentNode = currentNode.next;
    } while (currentNode != head);

    return head;
  }

  // Perform a reduce/aggregation function, like in JS.
  // Essentially, an operation is performed that aggregates element values.
  @Override
  public Object reduce(ListNode head, IReduceOperator operator, Object initial) {
    if (head == null) return initial;

    ListNode currentNode = head;
    Object result = initial;
    do {
      result = operator.operate(result, currentNode.element);
      currentNode = currentNode.next;
    } while (currentNode != head);

    return result;
  }

  // Perform filter operation, in which nodes whose values do not satisfy a
  // condition are removed using the delete() method.
  @Override
  public ListNode filter(ListNode head, IFilterCondition condition) {
    if (head == null) return null;

    // Start at tail and loop backwards to reduce awkward list iteration
    // in a continually modified list (avoid things like ConcurrentModificationException).
    ListNode currentNode = head.previous;
    do {
      if (!condition.isSatisfied(currentNode.element)) {
        if (size(head) == 1) {
          return null;
        }
        head = delete(head, currentNode.element);
      }
      currentNode = currentNode.previous;
    } while (currentNode != head || !condition.isSatisfied(currentNode.element));
    // Note loop stops at head, we check the case in which the head node
    // needs to be removed in the second conditional clause.

    return head;
  }
}
