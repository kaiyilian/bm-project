package com.bumu.arya.admin.soin.service.impl;

import com.bumu.arya.admin.soin.result.DistrictTree;
import com.bumu.arya.admin.soin.result.DistrictTreeV2Result;
import com.bumu.arya.admin.soin.service.SoinDistrictTreeService;
import com.bumu.arya.common.Constants;
import com.bumu.arya.soin.model.entity.AryaSoinDistrictEntity;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by CuiMengxin on 2015/11/4.
 */
@Service
public class SoinDistrictTreeServiceImpl implements SoinDistrictTreeService {

	Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);

	@Override
	public DistrictTree buildDistrictTree(List<AryaSoinDistrictEntity> districtEntities) {
		DistrictTree tree = new DistrictTree();
		Collections.sort(districtEntities, new Comparator<AryaSoinDistrictEntity>() {
			@Override
			public int compare(AryaSoinDistrictEntity o1, AryaSoinDistrictEntity o2) {
				return com.compare(o1.getDistrictName(), o2.getDistrictName());
			}
		});
		//把根地区放入tree中
		{
			for (int i = 0; i < districtEntities.size(); i++) {
				AryaSoinDistrictEntity districtEntity = districtEntities.get(i);
				if (null == districtEntity.getParentId() || districtEntity.getParentId().equals("")) {
					DistrictTree.DistrictTreeItem item = new DistrictTree.DistrictTreeItem();
					item.setHref(districtEntity.getId());
					item.setText(districtEntity.getDistrictName());
					if (districtEntity.getUpSuper() == null) {
						item.setUpSuper(Constants.FALSE);
					} else {
						item.setUpSuper(districtEntity.getUpSuper());
					}
					tree.add(item);
					districtEntities.remove(districtEntity);
					i--;
				}
			}
		}

		//递归构造子地区
		{
			for (DistrictTree.DistrictTreeItem item : tree) {
				DistrictTree.DistrictTreeItem subItem = buildDistrictTreeSubs(item, districtEntities);
			}
		}

		return tree;
	}

	@Override
	public DistrictTreeV2Result
	buildDistrictTreeV2(List<AryaSoinDistrictEntity> districtEntities) {
		DistrictTreeV2Result treeV2Result = new DistrictTreeV2Result();
		Collections.sort(districtEntities, new Comparator<AryaSoinDistrictEntity>() {
			@Override
			public int compare(AryaSoinDistrictEntity o1, AryaSoinDistrictEntity o2) {
				return com.compare(o1.getDistrictName(), o2.getDistrictName());
			}
		});
		for (AryaSoinDistrictEntity districtEntity : districtEntities) {
			DistrictTreeV2Result.DistrictTreeV2 treeV2 = new DistrictTreeV2Result.DistrictTreeV2();
			treeV2.setId(districtEntity.getId());
			treeV2.setName(districtEntity.getDistrictName());
			treeV2.setParentId(districtEntity.getParentId());
			if (!districtEntity.getId().endsWith("00")) {
				//不是00结尾的都是区县
				treeV2.setCanUpSuper(Constants.TRUE);
				if (districtEntity.getUpSuper() == null) {
					treeV2.setUpSuper(Constants.FALSE);
				} else {
					treeV2.setUpSuper(districtEntity.getUpSuper());
				}
			} else {
				treeV2.setCanUpSuper(Constants.FALSE);
			}
			treeV2Result.getTree().add(treeV2);
		}
		return treeV2Result;
	}

	private DistrictTree.DistrictTreeItem buildDistrictTreeSubs(DistrictTree.DistrictTreeItem item, List<AryaSoinDistrictEntity> districtEntities) {
		List<AryaSoinDistrictEntity> districtCopy = new ArrayList<>();
		districtCopy.addAll(districtEntities);
		DistrictTree.DistrictTreeItem subItem = null;
		for (AryaSoinDistrictEntity districtEntity : districtEntities) {
			if (districtEntity.getParentId().equals(item.getHref())) {
				subItem = new DistrictTree.DistrictTreeItem();
				subItem.setHref(districtEntity.getId());
				subItem.setText(districtEntity.getDistrictName());
				if (districtEntity.getUpSuper() == null) {
					subItem.setUpSuper(Constants.FALSE);
				} else {
					subItem.setUpSuper(districtEntity.getUpSuper());
				}
				item.addChildren(subItem);
				districtCopy.remove(districtEntity);
				buildDistrictTreeSubs(subItem, districtCopy);
			}
		}
		return subItem;
	}
}
