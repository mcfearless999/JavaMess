/**
 * Created by chrism on 5/25/17.
 */
public class stringQueue {
    node front;
    node back;

    stringQueue()
    {
        front = null;
        back = null;
    }
    stringQueue(node newNode)
    {
        front = newNode;
        back = newNode;
    }

    void add(node newNode)
    {
        if (front == null)
        {
            front = newNode;
            back = newNode;
        }else
        {
            back.next = newNode;
            back = newNode;
        }

    }

    boolean check()
    {
        if (front != null) return true;
        return false;
    }

    String dequeue()
    {
        if (front == null) return null;
        String temp = front.data;
        front = front.next;
        return temp;
    }

}



class node{
    String data;
    node next;

    node()
    {
        data = null;
        next = null;
    }
    node(String tempData)
    {
        data = tempData;
    }
}