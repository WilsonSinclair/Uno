public class DoubleLinkedPlayerList {

    private int size = 0;

    static class Node {
        Player player;
        Node previousNode;
        Node nextNode;

        public Node(Player player) {
            this.player = player;
        }
    }

    Node head, tail = null;

    public void addNode(Player player) {
        Node newNode = new Node(player);

        //If List is empty
        if (head == null) {
            head = tail = newNode;
            head.previousNode = null;
        }
        else {
            tail.nextNode = newNode;
            newNode.previousNode = tail;
            tail = newNode;
        }
        tail.nextNode = null;
        size++;
    }

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    public int getSize() {
        return size;
    }
}
