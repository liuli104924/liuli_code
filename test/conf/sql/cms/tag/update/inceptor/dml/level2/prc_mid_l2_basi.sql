create or replace procedure kbs.prc_mid_l2_basi(start_date       in integer, -- 数据日期
											       end_date       in integer, -- 数据日期
											       basi_code_list       in string, -- 基准列表(此参数必须输入，若跑全量请输入0，多指数列表以英文逗号分隔)
                                                   o_return_msg out string,   --失败的原因和错误信息
                                                   o_return_code out integer  --0 成功必须返回；-1 失败
                                                   ) is
------------------------------------------------------------------------
-- 模块名称：基准数据大宽表_盘后
-- 模块编号：
-- 开发人员：weiquan.luo
-- 目前版本：
-- 创建时间：
-- 版    权：
-- 功    能：计算基准数据大宽表_盘后的指标结果
-- 修改记录：
------------------------------------------------------------------------
--定义变量
v_prc_name    varchar2(200) := 'kbs.prc_mid_l2_basi'; --当前存储过程的名称
v_prc_desc    varchar2(1000):= '生成基准数据大宽表_盘后';  --存储过程功能描述
basi_codes     varchar2(200) DEFAULT '';        --要指定的需要跑的basi_code
basi_code_str  varchar2(200) DEFAULT '';        --用于辅助判断

begin
	o_return_code    := 0;
	o_return_msg     := '执行开始';
	monitor.prc_proc_exec_log(start_date,v_prc_name,v_prc_desc,o_return_code,o_return_msg);

	basi_code_str :=regexp_replace(basi_code_list,',','')
    basi_codes := basi_code_list
	o_return_msg := '基准数据大宽表_盘后表逻辑处理';
	set_env('hive.exec.dynamic.partition','true');
	set_env('hive.crud.dynamic.partition','true');
    EXECUTE IMMEDIATE 'delete from kbs.mid_l2_basi partition(busi_month) WHERE busi_month between substr('||start_date||',1,6) and substr('||end_date|| ',1,6) and t_date between '||start_date||' and '||end_date||' and ("'|| basi_code_str||'"="0" or find_in_set(basi_code,"'||basi_codes||'")>0)';
	commit;
	insert into kbs.mid_l2_basi partition(busi_month)
	 (
    busi_date,                  --业务日期
    t_date,                     --计算日期
    basi_code,                  --基准代码
    basi_name,                  --基准名称
    curr_code,                  --基准币种
    t_pont,                     --t日点位
    pont_rat,                   --点位收益率
    t1_pont,                    --t-1日点位
    basi_yld_d7,                --近7日基准收益率
    basi_yld_m1,                --近1月基准收益率
    basi_yld_m3,                --近3月基准收益率
    basi_yld_m6,                --近6月基准收益率
    basi_yld_y1,                --近1年基准收益率
    basi_yld_setp_d,            --成立以来基准收益率
    basi_ann_yld_setp_d,        --成立以来基准年化收益率
    basi_yld_st_year,           --本年以来基准收益率
    basi_ann_yld_st_year,       --本年以来基准年化收益率
    basi_max_reca_st_year,      --本年以来基准最大回撤
    basi_vix_st_year,           --本年以来基准波动率
    basi_sharpe_st_year,        --本年以来基准sharpe
    inta_mode,                  --计息方式
    valu_aprc,                  --估值（全价）
    t1_valu_aprc,               --t-1日估值（全价）
    upd_dur_ex,                 --行权修正久期
    upd_dur,                    --修正久期
    t1_upd_dur_ex,              --t-1日行权修正久期
    t1_upd_dur,                 --t-1日修正久期
    elem_rat,                   --成分收益率
    intrd_in,                   --利息收入
    unreal_pont  ,              --虚拟点位
    basi_yld_st_mth,            --本月以来基准收益率
    basi_max_reca_setp_d,       --成立以来基准最大回撤
    is_trd_dt,                  --是否交易日
    basi_vix_m1,                -- 近1月基准波动率
    basi_vix_y1,                -- 近1年基准波动率
    basi_vix_setp_d,            --成立以来基准波动率
    basi_sharpe_m1,             --近1月基准sharpe
    basi_sharpe_y1,             --近1年基准sharpe
    basi_sharpe_setp_d,         --成立以来基准sharpe
    basi_max_reca_m1,           --近1月基准最大回撤
    basi_max_reca_y1,           -- 近1年基准最大回撤
    basi_vix_m6,	            -- '近6月基准波动率',
    basi_sharpe_m6,         	--'近6月基准sharpe',
    basi_max_reca_m6,	        --'近6月基准最大回撤',
    upd_time,                   --源数据修改时间
    task_rs_id,                 --任务来源标识
    dm_src_info,                --来源信息
    dm_created_time,            --数据中台创建时间
    dm_created_by,              --数据中台创建者
    dm_updated_time,            --数据中台更新时间
    dm_updated_by               --数据中台更新者
	 )select
    t1.t_date as busi_date,                               --业务日期
    t1.t_date as t_date,                                  --计算日期
    t1.basi_code,                                       --基准代码
    t1.basi_name,                                       --基准名称
    t1.curr_code,                                       --基准币种
    t1.t_pont,                                          --t日点位
    t1.pont_rat,                                        --点位收益率
    t1.t1_pont,                                         --t-1日点位
    t2.basi_yld_d7,                                     --近7日基准收益率
    t2.basi_yld_m1,                                     --近1月基准收益率
    t2.basi_yld_m3,                                     --近3月基准收益率
    t2.basi_yld_m6,                                     --近6月基准收益率
    t2.basi_yld_y1,                                     --近1年基准收益率
    t2.basi_yld_setp_d,                                 --成立以来基准收益率
    t2.basi_ann_yld_setp_d,                             --成立以来基准年化收益率
    t2.basi_yld_st_year,                                --本年以来基准收益率
    t2.basi_ann_yld_st_year,                            --本年以来基准年化收益率
    t2.basi_max_reca_st_year,                           --本年以来基准最大回撤
    t2.basi_vix_st_year,                                --本年以来基准波动率
    t2.basi_sharpe_st_year,                             --本年以来基准sharpe
    t1.inta_mode,                                       --计息方式
    t3.valu_aprc,                                       --估值（全价）
    t3.t1_valu_aprc,                                    --t-1日估值（全价）
    t3.upd_dur_ex,                                      --行权修正久期
    t3.upd_dur,                                         --修正久期
    t3.t1_upd_dur_ex,                                   --t-1日行权修正久期
    t3.t1_upd_dur,                                      --t-1日修正久期
    case when t1.basi_clas in ('1','4') then t3.elem_rat when t1.basi_clas in ('2','3') then t1.pont_rat end as elem_rat,       --成分收益率
    t3.intrd_in,                                        --利息收入
    t2.unreal_pont,                                     --虚拟点位
    t2.basi_yld_st_mth,                                 --本月以来基准收益率
    t2.basi_max_reca_setp_d,                            --成立以来基准最大回撤
    t2.is_trd_dt,                                       --是否交易日
    t2.BASI_VIX_M1,                -- 近1月基准波动率
    t2.BASI_VIX_Y1,                -- 近1年基准波动率
    t2.BASI_VIX_SETP_D,            --成立以来基准波动率
    t2.BASI_SHARPE_M1,             --近1月基准Sharpe
    t2.BASI_SHARPE_Y1,             --近1年基准Sharpe
    t2.BASI_SHARPE_SETP_D,         --成立以来基准Sharpe
    t2.BASI_MAX_RECA_M1,           --近1月基准最大回撤
    t2.BASI_MAX_RECA_Y1,           -- 近1年基准最大回撤
    t2.BASI_VIX_M6,	            -- '近6月基准波动率',
    t2.BASI_SHARPE_M6,         	--'近6月基准Sharpe',
    t2.BASI_MAX_RECA_M6,	        --'近6月基准最大回撤',
    null as upd_time,                                   --源数据修改时间
    'prc_mid_l2_basi' as task_rs_id,                    --任务来源标识
    'mod.mid_l1_cal_basi' as dm_src_info,               --来源信息
    current_timestamp() as dm_created_time,             --数据中台创建时间
    'OUTLINE' as dm_created_by,                         --数据中台创建者
    current_timestamp() as dm_updated_time,             --数据中台更新时间
    'OUTLINE' as dm_updated_by,                         --数据中台更新者
    substr(t1.t_date,1,6) as busi_month                                --业务月份(分区字段)
     from(
        select
            basi_code,basi_name,curr_code,t_pont,pont_rat,t1_pont,inta_mode,basi_clas,t_date
        from mod.mid_l1_cal_basi where t_date BETWEEN start_date AND end_date
            and busi_month BETWEEN substr(start_date,1,6) AND substr(end_date,1,6)
            AND (basi_code_str ='0' or find_in_set(basi_code,basi_codes)>0)
    ) t1
     left join kbs.mid_l2_basi_tmp t2 on t1.basi_code = t2.basi_code and t1.t_date = t2.t_date
     left join (
     select a.basi_code,
            a.t_date,
           sum(NVL(a.t1_elem_wght * b.ant_unit_nval,0)) as elem_rat,
           sum(NVL(a.t1_elem_wght * f.valu_aprc,0)) as valu_aprc,
           sum(NVL(a.t2_elem_wght * d.valu_aprc,0)) as t1_valu_aprc,
           sum(NVL(a.t_elem_wght * f.upd_dur,0)) as upd_dur,
           sum(NVL(a.t1_elem_wght * d.upd_dur,0)) as t1_upd_dur,
           sum(NVL(a.t_elem_wght * f.upd_dur_ex,0)) as upd_dur_ex,
           sum(NVL(a.t1_elem_wght * d.upd_dur_ex,0)) as t1_upd_dur_ex,
           sum(NVL(a.t1_elem_wght ,0) * (cast(nvl(f.accr_intr,0) as double )- cast (nvl(d.accr_intr,0) as double) + cast (nvl(f.divd,0) as  double ))) as intrd_in
            from (
                select basi_code,secu_id, t_date,t1_elem_wght,t2_elem_wght,t_elem_wght
                from mod.mid_l1_cal_basi_dtl
                    where t_date BETWEEN start_date AND end_date
                    and busi_month BETWEEN substr(start_date,1,6) AND substr(end_date,1,6)
            ) a
            left join mod.mid_l1_cal_stk b on a.secu_id = b.secu_id and b.t_date = a.t_date and b.busi_month BETWEEN substr(start_date,1,6) AND substr(end_date,1,6)
            left join (
                select secu_id,valu_aprc,upd_dur,accr_intr,divd,upd_dur_ex,t_date from mod.mid_l1_cal_bond
                    where t_date BETWEEN start_date AND end_date
                    and busi_month BETWEEN substr(start_date,1,6) AND substr(end_date,1,6)
                    and (end_dt > t_date or end_dt is null)
            ) f on a.secu_id = f.secu_id and a.t_date = f.t_date
            left join (
                select t.secu_id,t.secu_name,t.valu_aprc,t.upd_dur_ex,t.upd_dur,t.accr_intr,t.cald_date
                    from (
                        select a.secu_id,a.secu_name,a.valu_aprc,a.upd_dur_ex,a.upd_dur,a.accr_intr,trd.cald_date,row_number() over(partition by a.secu_id,trd.cald_date order by a.t_date desc) as n
                            from mod.mid_l1_cal_bond a
                            inner join (
                                select cald_date from agg.trd_cald_dtl
                                    where cald_id = 1 AND cald_date BETWEEN start_date AND end_date) trd on 1 = 1
                where a.t_date < trd.cald_date and a.busi_month <= substr(end_date,1,6) and (end_dt > trd.cald_date or end_dt is null)) t where t.n = 1
            ) d on a.secu_id = d.secu_id and a.t_date = d.cald_date group by a.basi_code,a.t_date
        ) t3 on t1.basi_code = t3.basi_code and t1.t_date = t3.t_date

	commit;
  o_return_code := 0;
  o_return_msg  := '执行成功';
  monitor.prc_proc_exec_log(start_date,v_prc_name,v_prc_desc,o_return_code,o_return_msg);
  -- ===============================================================================
  -- 出错处理
  -- ===============================================================================
exception
  when others then
    rollback;
    o_return_code    := -1;
    o_return_msg     := o_return_msg || sqlcode || sqlerrm;
    monitor.prc_proc_exec_log(start_date,
                             v_prc_name,
                             v_prc_desc,
                             o_return_code,
                             o_return_msg
                             );
end prc_mid_l2_basi