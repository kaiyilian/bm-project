package com.bumu.bran.admin.corporation.helper;

import com.bumu.bran.handler.NodeHandler;
import com.bumu.common.Node;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * @author majun
 * @date 2016/10/10
 */
public abstract class NodeCoreHelper<T extends Node, E> implements NodeHandler<T, E> {

    private Logger logger = LoggerFactory.getLogger(NodeCoreHelper.class);

    @Override
    public List<T> findChildrenNodes(List<T> parents, List<T> all) {
        List<T> children = new LinkedList<>();
        for (int i = 0; i < parents.size(); i++) {
            T t = parents.get(i);
            for (int j = 0; j < all.size(); j++) {
                if (t.getId().equals(all.get(j).getParentId())) {
                    children.add(all.get(j));
                }
            }

        }
        return children;
    }

    @Override
    public int calculateNodeCount(Node parentNode, List<T> allList, int count) {
        logger.debug("before count...:" + count);
        logger.debug("parentNode.getName():" + parentNode.getName());
        for (int i = 0; i < allList.size(); i++) {
            T node = allList.get(i);
            if (node.getParentId().equals(parentNode.getId())) {
                logger.debug("node.getName():" + node.getName());
                count += node.getCount();
                logger.debug("after count...:" + count);
                count = calculateNodeCount(node, allList, count);

            }
        }
        logger.debug("finally count... " + count);
        return count;
    }

    /**
     * 先查询出父节点放入todo的List集合,然后遍历整个树节点all的List集合,查询父节点的所有子节点,
     * 在all中删除该父节点,在todo中添加该子节点
     *
     * @param todoList
     * @return
     */
    @Override
    public List<T> generateNodeTree(List<T> todoList) throws Exception {
        // 响应参数
        List<T> result = new LinkedList<>();

        // 参数
        LinkedList<T> all = new LinkedList<>(todoList);
        LinkedList<T> parents = (LinkedList<T>) findParentNodes(all);

        while (parents.size() > 0) {
            // 先查询出父节点
            for (T t : parents) {
                t.setCount(this.calculateNodeCount(t, all, t.getCount()));
                result.add(t);

            }
            all.removeAll(parents);
            parents = (LinkedList<T>) findChildrenNodes(parents, all);
        }
        return result;
    }

    @Override
    public List<T> findParentNodes(List<T> all) {
        List<T> parents = new LinkedList<>();
        for (int i = 0; i < all.size(); i++) {
            T t = all.get(i);
            System.out.println(t.getId());
            if (StringUtils.isBlank(t.getParentId()) || t.getParentId().equals("null")) {
                parents.add(t);
                all.remove(t);
                i = -1;
            }
        }
        return parents;
    }

    @Override
    public List<T> nodeConverter(List<E> todoList) {
        return null;
    }

    @Override
    public List<Node> echartsNodeConverter(List<T> sub, String title) {
        Node node = new Node();
        node.setName(title);
        List<Node> list = new ArrayList<>();
        list.add(node);
        return list;
    }
}
