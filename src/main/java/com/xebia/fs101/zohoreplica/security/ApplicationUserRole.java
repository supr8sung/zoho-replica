//package com.xebia.fs101.zohoreplica.security;
//
//import com.google.common.collect.Sets;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import static com.xebia.fs101.zohoreplica.security.ApplicationUserPermission.USER_CHECKIN;
//import static com.xebia.fs101.zohoreplica.security.ApplicationUserPermission.USER_CHECKOUT;
//import static com.xebia.fs101.zohoreplica.security.ApplicationUserPermission.USER_READ;
//import static com.xebia.fs101.zohoreplica.security.ApplicationUserPermission.USER_WRITE;
//public enum ApplicationUserRole {
//
//    USER(Sets.newHashSet(USER_CHECKIN, USER_CHECKOUT)),
//    ADMIN(Sets.newHashSet(USER_READ, USER_WRITE, USER_CHECKIN, USER_CHECKOUT));
////    ADMINTRAINEE(Sets.newHashSet(USER_READ, USER_WRITE, USER_CHECKIN, USER_CHECKOUT));
//
//
//    private final Set<ApplicationUserPermission> permissions;
//
//    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
//        this.permissions = permissions;
//    }
//
//    public Set<ApplicationUserPermission> getPermissions() {
//        return permissions;
//    }
//    public String getRoleName(){
//        return  "ROLE_"+this.name();
//    }
//
//    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
//        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
//                .map(e -> new SimpleGrantedAuthority(e.getPermission()))
//                .collect(Collectors.toSet());
//        permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
//        return permissions;
//    }
//}
