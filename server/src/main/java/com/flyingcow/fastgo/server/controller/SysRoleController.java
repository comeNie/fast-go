package com.flyingcow.fastgo.server.controller;

import com.flyingcow.fastgo.server.common.utils.PageUtils;
import com.flyingcow.fastgo.server.common.utils.Query;
import com.flyingcow.fastgo.server.common.utils.Result;
import com.flyingcow.fastgo.server.common.utils.ResultUtil;
import com.flyingcow.fastgo.server.entity.SysRoleEntity;
import com.flyingcow.fastgo.server.service.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
/**
 * 
 *
 * @author hushangjie
 * @email 979783618@qq.com
 * @date 2018-04-12 11:11:45
 */
@RestController
@RequestMapping("sys_role")
public class SysRoleController {
    protected final static Logger log = LoggerFactory.getLogger(SysRoleController.class);

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询所有纪录,供后台使用
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority('sys_role_list')")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据,params中可以包含查询条件
        Query query = new Query(params);
        return getResult(params, query);
    }

    /**
     * 查询某个资源,供后台使用
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys_role_info')")
    public Result info(@PathVariable("id") Integer id) {

        SysRoleEntity sysRole = sysRoleService.queryObject(id);
        return new Result("sysRole", sysRole);
    }

    /**
     * 新增某个系统级别资源,供后台使用
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority('sys_role_add')")
    public Result save(@RequestBody SysRoleEntity sysRole) {
        sysRoleService.save(sysRole);

        return ResultUtil.success();
    }

    /**
     * 修改资源,供后台使用
     */
    @PutMapping("")
    @PreAuthorize("hasAuthority('sys_role_update')")
    public Result update(@RequestBody SysRoleEntity sysRole) {
        sysRoleService.update(sysRole);

        return ResultUtil.success();
    }

    /**
     * 删除某个资源,供后台使用
     */
    @DeleteMapping("")
    @PreAuthorize("hasAuthority('sys_role_delete')")
    public Result delete(@RequestBody Integer[] ids) {
        sysRoleService.deleteBatchLogically(ids);

        return ResultUtil.success();
    }


    /**
       * 是否获取分页,没有page和limit参数则直接获取所有列表
       * 使用方法   /资源名称?page=1&limit=10&param1=?&sidx=[asc||desc]&order=xx
       * param1指查询参数,sidx指升序或降序,order排序字段
       * @param params
       * @param query
       * @return
       */
    private Result getResult(@RequestParam Map<String, Object> params, Query query) {
        if (params.get("page") != null && params.get("limit") != null) {
            List<SysRoleEntity> sysRoleList = sysRoleService.queryList(query);
            Integer total = sysRoleService.queryTotal(query);
            PageUtils pageUtil = new PageUtils(sysRoleList, total, query.getSize(), query.getPage());
            return new Result(pageUtil);
        } else {
            List<SysRoleEntity> sysRoleList = sysRoleService.queryList(query);
            return new Result("list", sysRoleList);
        }
    }
}
