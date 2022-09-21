package com.techvg.hrms.repository;

import com.techvg.hrms.domain.Employee;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class EmployeeRepositoryWithBagRelationshipsImpl implements EmployeeRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Employee> fetchBagRelationships(Optional<Employee> employee) {
        return employee.map(this::fetchSecurityPermissions).map(this::fetchSecurityRoles);
    }

    @Override
    public Page<Employee> fetchBagRelationships(Page<Employee> employees) {
        return new PageImpl<>(fetchBagRelationships(employees.getContent()), employees.getPageable(), employees.getTotalElements());
    }

    @Override
    public List<Employee> fetchBagRelationships(List<Employee> employees) {
        return Optional.of(employees).map(this::fetchSecurityPermissions).map(this::fetchSecurityRoles).orElse(Collections.emptyList());
    }

    Employee fetchSecurityPermissions(Employee result) {
        return entityManager
            .createQuery(
                "select employee from Employee employee left join fetch employee.securityPermissions where employee is :employee",
                Employee.class
            )
            .setParameter("employee", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Employee> fetchSecurityPermissions(List<Employee> employees) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, employees.size()).forEach(index -> order.put(employees.get(index).getId(), index));
        List<Employee> result = entityManager
            .createQuery(
                "select distinct employee from Employee employee left join fetch employee.securityPermissions where employee in :employees",
                Employee.class
            )
            .setParameter("employees", employees)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Employee fetchSecurityRoles(Employee result) {
        return entityManager
            .createQuery(
                "select employee from Employee employee left join fetch employee.securityRoles where employee is :employee",
                Employee.class
            )
            .setParameter("employee", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Employee> fetchSecurityRoles(List<Employee> employees) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, employees.size()).forEach(index -> order.put(employees.get(index).getId(), index));
        List<Employee> result = entityManager
            .createQuery(
                "select distinct employee from Employee employee left join fetch employee.securityRoles where employee in :employees",
                Employee.class
            )
            .setParameter("employees", employees)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
