package DataBean;

import java.util.List;

/**
 * Created by admin on 2016/12/2.
 */
/*
*
* 描    述：V2EXGsonBean
* 作    者：ksheng
* 时    间：2016/12/2$ 21:49$.
*/
public class PandaGsonBean {
    public int errno;
    public String errmsg;
    public DataBean data;
    public String authseq;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getAuthseq() {
        return authseq;
    }

    public void setAuthseq(String authseq) {
        this.authseq = authseq;
    }

    public class DataBean {
        public String total;
        public TypeBean type;
        public List<ItemsBean> items;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public TypeBean getType() {
            return type;
        }

        public void setType(TypeBean type) {
            this.type = type;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public class TypeBean {
            public String ename;
            public String cname;

            public String getEname() {
                return ename;
            }

            public void setEname(String ename) {
                this.ename = ename;
            }

            public String getCname() {
                return cname;
            }

            public void setCname(String cname) {
                this.cname = cname;
            }
        }

        public class ItemsBean {
            public String id;
            public String ver;
            public String createtime;
            public String updatetime;
            public String name;
            public String hostid;
            public String person_num;
            public String announcement;
            public ClassificationBean classification;
            public PicturesBean pictures;
            public String status;
            public String start_time;
            public String end_time;
            public String duration;
            public String schedule;
            public String remind_switch;
            public String remind_content;
            public String level;
            public String stream_status;
            public String classify_switch;
            public String reliable;
            public String banned_reason;
            public String unlock_time;
            public String speak_interval;
            public String person_num_thres;
            public String reduce_ratio;
            public String person_switch;
            public String watermark_switch;
            public String watermark_loc;
            public String account_status;
            public String person_src;
            public String display_type;
            public String tag;
            public String tag_switch;
            public String tag_color;
            public String rcmd_ratio;
            public String show_pos;
            public String rtype_usable;
            public String room_type;
            public String rtype_value;
            public String style_type;
            public String room_key;
            public String fans;
            public UserinfoBean userinfo;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getVer() {
                return ver;
            }

            public void setVer(String ver) {
                this.ver = ver;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(String updatetime) {
                this.updatetime = updatetime;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getHostid() {
                return hostid;
            }

            public void setHostid(String hostid) {
                this.hostid = hostid;
            }

            public String getPerson_num() {
                return person_num;
            }

            public void setPerson_num(String person_num) {
                this.person_num = person_num;
            }

            public String getAnnouncement() {
                return announcement;
            }

            public void setAnnouncement(String announcement) {
                this.announcement = announcement;
            }

            public ClassificationBean getClassification() {
                return classification;
            }

            public void setClassification(ClassificationBean classification) {
                this.classification = classification;
            }

            public PicturesBean getPictures() {
                return pictures;
            }

            public void setPictures(PicturesBean pictures) {
                this.pictures = pictures;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getSchedule() {
                return schedule;
            }

            public void setSchedule(String schedule) {
                this.schedule = schedule;
            }

            public String getRemind_switch() {
                return remind_switch;
            }

            public void setRemind_switch(String remind_switch) {
                this.remind_switch = remind_switch;
            }

            public String getRemind_content() {
                return remind_content;
            }

            public void setRemind_content(String remind_content) {
                this.remind_content = remind_content;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getStream_status() {
                return stream_status;
            }

            public void setStream_status(String stream_status) {
                this.stream_status = stream_status;
            }

            public String getClassify_switch() {
                return classify_switch;
            }

            public void setClassify_switch(String classify_switch) {
                this.classify_switch = classify_switch;
            }

            public String getReliable() {
                return reliable;
            }

            public void setReliable(String reliable) {
                this.reliable = reliable;
            }

            public String getBanned_reason() {
                return banned_reason;
            }

            public void setBanned_reason(String banned_reason) {
                this.banned_reason = banned_reason;
            }

            public String getUnlock_time() {
                return unlock_time;
            }

            public void setUnlock_time(String unlock_time) {
                this.unlock_time = unlock_time;
            }

            public String getSpeak_interval() {
                return speak_interval;
            }

            public void setSpeak_interval(String speak_interval) {
                this.speak_interval = speak_interval;
            }

            public String getPerson_num_thres() {
                return person_num_thres;
            }

            public void setPerson_num_thres(String person_num_thres) {
                this.person_num_thres = person_num_thres;
            }

            public String getReduce_ratio() {
                return reduce_ratio;
            }

            public void setReduce_ratio(String reduce_ratio) {
                this.reduce_ratio = reduce_ratio;
            }

            public String getPerson_switch() {
                return person_switch;
            }

            public void setPerson_switch(String person_switch) {
                this.person_switch = person_switch;
            }

            public String getWatermark_switch() {
                return watermark_switch;
            }

            public void setWatermark_switch(String watermark_switch) {
                this.watermark_switch = watermark_switch;
            }

            public String getWatermark_loc() {
                return watermark_loc;
            }

            public void setWatermark_loc(String watermark_loc) {
                this.watermark_loc = watermark_loc;
            }

            public String getAccount_status() {
                return account_status;
            }

            public void setAccount_status(String account_status) {
                this.account_status = account_status;
            }

            public String getPerson_src() {
                return person_src;
            }

            public void setPerson_src(String person_src) {
                this.person_src = person_src;
            }

            public String getDisplay_type() {
                return display_type;
            }

            public void setDisplay_type(String display_type) {
                this.display_type = display_type;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getTag_switch() {
                return tag_switch;
            }

            public void setTag_switch(String tag_switch) {
                this.tag_switch = tag_switch;
            }

            public String getTag_color() {
                return tag_color;
            }

            public void setTag_color(String tag_color) {
                this.tag_color = tag_color;
            }

            public String getRcmd_ratio() {
                return rcmd_ratio;
            }

            public void setRcmd_ratio(String rcmd_ratio) {
                this.rcmd_ratio = rcmd_ratio;
            }

            public String getShow_pos() {
                return show_pos;
            }

            public void setShow_pos(String show_pos) {
                this.show_pos = show_pos;
            }

            public String getRtype_usable() {
                return rtype_usable;
            }

            public void setRtype_usable(String rtype_usable) {
                this.rtype_usable = rtype_usable;
            }

            public String getRoom_type() {
                return room_type;
            }

            public void setRoom_type(String room_type) {
                this.room_type = room_type;
            }

            public String getRtype_value() {
                return rtype_value;
            }

            public void setRtype_value(String rtype_value) {
                this.rtype_value = rtype_value;
            }

            public String getStyle_type() {
                return style_type;
            }

            public void setStyle_type(String style_type) {
                this.style_type = style_type;
            }

            public String getRoom_key() {
                return room_key;
            }

            public void setRoom_key(String room_key) {
                this.room_key = room_key;
            }

            public String getFans() {
                return fans;
            }

            public void setFans(String fans) {
                this.fans = fans;
            }

            public UserinfoBean getUserinfo() {
                return userinfo;
            }

            public void setUserinfo(UserinfoBean userinfo) {
                this.userinfo = userinfo;
            }

            public class ClassificationBean {
                public String cname;
                public String ename;

                public String getCname() {
                    return cname;
                }

                public void setCname(String cname) {
                    this.cname = cname;
                }

                public String getEname() {
                    return ename;
                }

                public void setEname(String ename) {
                    this.ename = ename;
                }
            }

            public class PicturesBean {
                public String img;

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }
            }

            public class UserinfoBean {
                public String nickName;
                public int rid;
                public String avatar;
                public String userName;

                public String getNickName() {
                    return nickName;
                }

                public void setNickName(String nickName) {
                    this.nickName = nickName;
                }

                public int getRid() {
                    return rid;
                }

                public void setRid(int rid) {
                    this.rid = rid;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }
            }
        }
    }
}
