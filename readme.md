git   分布式版本控制系统



集中式版本控制系统  CVS(免费的) SVN(免费的)   IBM的ClearCase（收费的）微软的VSS（收费的）

分布式版本控制系统  git  BitKeeper Mercurial  Bazaar


		创建版本库  mkdir 文件夹名称   
		进入文件夹   cd 文件夹名称
		初始化git    git init
		
	把文件添加到仓库  git add  文件名	
	把文件提交到仓库  git commit -m "本次提交的说明"
												例如：	$ git add file1.txt
														$ git add file2.txt file3.txt
														$ git commit -m "add 3 files."	
								
	
	git status 查看仓库当前的状态	
	git diff 文件名  查看文件与之前的不同

提交修改和提交新文件是一样的两步   1.git add 2.git commit 
	git log  查看历史记录

 	git log --pretty=oneline  把历史记录显示成一行


	回退到上一个版本
		git reset --hard HEAD^
	查看文件
	cat 文件夹名称
	
	回退到指定版本  git reset --hard  版本号

	记录每一次命令  	git reflog

	查看工作区和版本库里面最新版本的区别    git diff HEAD -- 文件名
	
	把文件在工作区的修改全部撤销 	git checkout -- 文件名（文件做了修改还没有 add 的时候）
	
	把暂存区的修改撤销掉 		git reset HEAD file （ 文件做了修改 已经 add 到暂存区）


My stupid boss still prefers SVN



	