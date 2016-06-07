package com.szzaq.jointdefence.model;

public class UserInfo {
    String url;
    String username;
    String email;
    String[] role;
    String asFireMan;
    String asStaff;
    String asMaintainer;
    String asGridAdmin;
    Profile profile;
    int id;

    public int getId() {
        return id;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String[] getRole() {
        return role;
    }


    public void setRole(String[] role) {
        this.role = role;
    }


    public String getAsFireMan() {
        return asFireMan;
    }


    public void setAsFireMan(String asFireMan) {
        this.asFireMan = asFireMan;
    }


    public String getAsStaff() {
        return asStaff;
    }


    public void setAsStaff(String asStaff) {
        this.asStaff = asStaff;
    }


    public String getAsMaintainer() {
        return asMaintainer;
    }


    public void setAsMaintainer(String asMaintainer) {
        this.asMaintainer = asMaintainer;
    }


    public String getAsGridAdmin() {
        return asGridAdmin;
    }


    public void setAsGridAdmin(String asGridAdmin) {
        this.asGridAdmin = asGridAdmin;
    }


    public Profile getProfile() {
        return profile;
    }


    public void setProfile(Profile profile) {
        this.profile = profile;
    }


    public static class Profile {
        String url;
        String user;
        String nickname;
        String phone;
        String portrait_url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPortrait_url() {
            return portrait_url;
        }

        public void setPortrait_url(String portrait_url) {
            this.portrait_url = portrait_url;
        }


    }
}
