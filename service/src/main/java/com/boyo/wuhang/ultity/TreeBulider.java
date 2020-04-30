package com.boyo.wuhang.ultity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeBulider {

	/**
	 * 方法名称：Tree
	 *
	 * @param Pkey
	 * @param Fkey
	 * @param Dkey
	 * @param Ckey
	 * @param Source
	 */
	public static final void Tree(String Pkey,
	                              String Fkey,
	                              String Dkey,
	                              String Ckey,
	                              List<Map<String, Object>> Source,
	                              Integer Level) {
		if (Level == null) Level = maxLevel(Source, Dkey);
		if (Level <= 1) return;
		List<Map<String, Object>> temp = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> tp1 : Source) {
			if (tp1.get(Dkey).equals(Level)) {
				for (Map<String, Object> tp2 : Source) {
					if (tp1.get(Fkey) != null && tp1.get(Fkey).equals(tp2.get(Pkey))) {
						if (!tp2.containsKey(Ckey)) {
							tp2.put(Ckey, new ArrayList<Map<String, Object>>());
						}
						((ArrayList<Map<String, Object>>) tp2.get(Ckey)).add(tp1);
						break;
					}
				}
				temp.add(tp1);
			}
		}
		Source.removeAll(temp);
		Level--;
		Tree(Pkey, Fkey, Dkey, Ckey, Source, Level);
	}


	private static int maxLevel(List<Map<String, Object>> Source, String Dkey) {
		int maxLevel = 0;
		for (Map<String, Object> data : Source) {
			Integer temp = (Integer) data.get(Dkey);
			if (temp > maxLevel) maxLevel = temp;
		}
		return maxLevel;
	}

	/**
	 * 方法名称：allTree 递归遍历 查询子节点
	 *
	 * @param Pkey   主键
	 * @param Fkey   父级主键
	 * @param Vkey   父级主键的值 根节点
	 * @param Ckey   items
	 * @param Source list
	 */
	public static final void allTree(String Pkey,
	                                 String Fkey,
	                                 Object Vkey,
	                                 String Ckey,
	                                 List<Map<String, Object>> Source) {
		List<Map<String, Object>> temp = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> tp1 : Source) {
			//遍历全部节点 查找当前层级 --- 顶级父节点为 null 【tp1.get(Fkey)!=null &&】
			if ((Vkey == null && tp1.get(Fkey) == null) || (Vkey != null && Vkey.equals(tp1.get(Fkey)))) {
				//当前层级节点 --- 一级节点tp1 【查找下级 -- 移动=新增+删除】
				for (Map<String, Object> tp2 : Source) {
					if (tp2.get(Fkey) != null && tp2.get(Fkey).equals(tp1.get(Pkey))) {
						//下级节点 --- 二级节点tp2 递归查找下级
						allTree(Pkey, Fkey, tp2.get(Fkey), Ckey, Source);

						if (!tp1.containsKey(Ckey)) {
							tp1.put(Ckey, new ArrayList<Map<String, Object>>());
						}
						((ArrayList<Map<String, Object>>) tp1.get(Ckey)).add(tp2);
						temp.add(tp2);
					}
				}
			}
		}
		if (temp.size() > 0) {
			Source.removeAll(temp);
		}
	}
}