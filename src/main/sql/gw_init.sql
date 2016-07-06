/*
INSERT INTO `c_modual` VALUES 
(1,'产品管理','sys_good','',0,0,0,'websites_mag'),
(2,'系统管理','sys_setting','',1,0,0,'system_mag'),
(3,'资源菜单管理','sys_modual','sys/resource/indexModuleManage.jsp',0,2,0,'resources_mag'),
(4,'数据字典管理','sys_basedict','sys/dict/indexDictManage.jsp',1,2,0,'dict_config_mag'),
(5,'类目标准属性管理','sys_prop','web/prop/indexPropManage.jsp',1,1,0,'websites_mag'),
(6,'商品类目管理','sys_classifcation','web/classifcation/IndexClassifcationManage.jsp',0,1,0,'websites_mag');*/

INSERT INTO `c_user` VALUES (1,'超级管理员','superadmin','jz20091218','超级管理员',now(),now());
INSERT INTO `c_user` VALUES (2,'管理员1','admin1','jz20091218','管理员1',now(),now());
INSERT INTO `c_user` VALUES (3,'管理员2','admin2','jz20091218','管理员2',now(),now());


INSERT INTO `c_modual` VALUES (1, '商品管理', 'sys_good', '', 0, 0, 0, 'modulebutton_menu');
INSERT INTO `c_modual` VALUES (2, '系统管理', 'sys_setting', '', 1, 0, 0, 'system_mag');
INSERT INTO `c_modual` VALUES (3, '资源菜单管理', 'sys_modual', 'sys/resource/indexModuleManage.jsp', 0, 2, 0, 'resources_mag');
INSERT INTO `c_modual` VALUES (7, '数据字典管理', 'sys_basedict', 'sys/dict/indexDictManage.jsp', 1, 2, NULL, 'dict_config_mag');
INSERT INTO `c_modual` VALUES (8, '商品属性管理', 'sys_prop', 'web/prop/indexPropManage.jsp', 1, 1, NULL, 'sysmenu_mag');
INSERT INTO `c_modual` VALUES (9, '商品类目管理', 'sys_classifcation', 'web/classifcation/IndexClassifcationManage.jsp', 0, 1, NULL, 'organ_mag');
