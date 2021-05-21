package com.ftn.tseo2021.sf1513282018.studentService.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class CustomPrincipal implements UserDetails {

	private static final long serialVersionUID = 4711920492695245783L;
	
	private String password;
	
	private final Integer id;

	private final String username;
	
	private final String firstName;
	
	private final String lastName;
	
	private final Integer institutionId;
	
	private final Integer ownerId;

	private final Set<GrantedAuthority> authorities;
	
	private final boolean accountNonExpired;

	private final boolean accountNonLocked;

	private final boolean credentialsNonExpired;

	private final boolean enabled;
	
	public CustomPrincipal(Integer id, String username, String firstName, String lastName, String password, Integer institutionId, Integer ownerId, Collection<? extends GrantedAuthority> authorities) {
		this(id, username, firstName, lastName, password, institutionId, ownerId, true, true, true, true, authorities);
	}
	
	public CustomPrincipal(Integer id, String username, String firstName, String lastName, String password, Integer institutionId, Integer ownerId, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		Assert.isTrue(id != null && institutionId != null && username != null && !"".equals(username) && password != null,
				"Cannot pass null or empty values to constructor");
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.institutionId = institutionId;
		this.ownerId = ownerId;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	
	private final SimpleGrantedAuthority superadminAuthority = new SimpleGrantedAuthority("SUPERADMIN");
	private final SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ADMIN");
	private final SimpleGrantedAuthority teacherAuthority = new SimpleGrantedAuthority("TEACHER");
	private final SimpleGrantedAuthority studentAuthority = new SimpleGrantedAuthority("STUDENT");
	
	public boolean isSuperadmin() {
		if (this.authorities.contains(superadminAuthority)) return true;
		return false;
	}
	
	public boolean isAdmin() {
		if (this.authorities.contains(adminAuthority)) return true;
		return false;
	}
	
	public boolean isTeacher() {
		if (this.authorities.contains(teacherAuthority)) return true;
		return false;
	}

	public boolean isStudent() {
		if (this.authorities.contains(studentAuthority)) return true;
		return false;
	}
	
	public Integer getTeacherId() {
		if (!this.isTeacher()) throw new RuntimeException("Principal is not teacher hence you can not access teacherId");
		return this.ownerId;
	}
	
	public Integer getStudentId() {
		if (!this.isStudent()) throw new RuntimeException("Principal is not student hence you can not access studentId");
		return this.ownerId;
	}
	
	public Integer getId() {
		return this.id;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public Integer getInstitutionId() {
		return this.institutionId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (GrantedAuthority auth : this.authorities) sb.append(String.format("%s, ", auth.getAuthority()));
		sb.delete(sb.length()-2, sb.length());
		return String.format("id: %d\nusername: %s\ninstitutionId: %d\nauthorities: %s", this.id, this.username, this.institutionId, sb.toString());
	}
	
	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
		// Ensure array iteration order is predictable (as per
		// UserDetails.getAuthorities() contract and SEC-717)
		SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new AuthorityComparator());
		for (GrantedAuthority grantedAuthority : authorities) {
			Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
			sortedAuthorities.add(grantedAuthority);
		}
		return sortedAuthorities;
	}
	
	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {

		private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

		@Override
		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
			// Neither should ever be null as each entry is checked before adding it to
			// the set. If the authority is null, it is a custom authority and should
			// precede others.
			if (g2.getAuthority() == null) {
				return -1;
			}
			if (g1.getAuthority() == null) {
				return 1;
			}
			return g1.getAuthority().compareTo(g2.getAuthority());
		}

	}
	
}
