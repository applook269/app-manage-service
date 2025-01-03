package com.hooke.zdl.admin.module.business.service;


import com.hooke.zdl.admin.common.domain.PageParam;
import com.hooke.zdl.admin.common.domain.PageResult;
import com.hooke.zdl.admin.common.util.SmartPageUtil;
import com.hooke.zdl.admin.module.business.dao.TeamMapper;
import com.hooke.zdl.admin.module.business.dao.TeamMemberMapper;
import com.hooke.zdl.admin.module.business.entity.Team;
import com.hooke.zdl.admin.module.business.entity.TeamMember;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamAdminService {
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private TeamMemberMapper teamMemberMapper;

    public PageResult<Team> pageTeam(Team team, PageParam pageParam) {
        Page<Team> page = SmartPageUtil.convert2PageQuery(pageParam);
        Page<Team> walletPage = teamMapper.paginate(page, QueryWrapper.create(team));
        return SmartPageUtil.convert2PageResult(page, walletPage.getRecords(), Team.class);
    }

    public List<TeamMember> listTeamMember(TeamMember teamMember) {
        return teamMemberMapper.selectListByQuery(QueryWrapper.create(teamMember));
    }
}
