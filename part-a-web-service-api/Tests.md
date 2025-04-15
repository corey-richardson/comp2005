# Controller Layer

## F1 – Never Admitted
- [x] `getNeverAdmitted_shouldReturnExpectedPatients`
- [x] `getNeverAdmitted_shouldReturnEmptyList_whenNoPatientsExist`
- [x] `getNeverAdmitted_shouldHandleServiceExceptionGracefully`

## F2 – Readmitted Within 7 Days
- [ ] `getReadmittedSevenDays_shouldReturnReadmittedPatients`
- [ ] `getReadmittedSevenDays_shouldReturnEmptyList_whenNoneMatch`
- [ ] `getReadmittedSevenDays_shouldHandleServiceExceptionGracefully`

## F3 – Month with Most Admissions
- [ ] `getMonthWithMost_shouldReturnCorrectMonth`
- [ ] `getMonthWithMost_shouldHandleTieBetweenMonths`
- [ ] `getMonthWithMost_shouldHandleNoAdmissionsGracefully`

## F4 – Multiple Staff per Patient
- [ ] `getMultipleStaff_shouldReturnCorrectPatients`
- [ ] `getMultipleStaff_shouldReturnEmptyList_whenAllHaveSingleStaff`
- [ ] `getMultipleStaff_shouldHandleServiceExceptionGracefully`

---

# Service Layer

## F1 – Logic: Filter Never-Admitted
- [ ] `getPatientsNeverAdmitted_shouldExcludeAdmittedPatients`
- [ ] `getPatientsNeverAdmitted_shouldReturnAll_ifNoneAdmitted`
- [ ] `getPatientsNeverAdmitted_shouldReturnEmpty_ifAllAdmitted`

## F2 – Logic: Readmission Within 7 Days
- [ ] `getReadmittedWithin7Days_shouldDetectReAdmission`
- [ ] `getReadmittedWithin7Days_shouldIgnoreAdmissionsAfter7Days`
- [ ] `getReadmittedWithin7Days_shouldHandleUnorderedAdmissions`

## F3 – Logic: Most Admissions by Month
- [ ] `getMonthWithMostAdmissions_shouldCountCorrectly`
- [ ] `getMonthWithMostAdmissions_shouldReturnFirstMonth_inTie`
- [ ] `getMonthWithMostAdmissions_shouldReturnNull_ifNoAdmissions`

## F4 – Logic: Patients With Multiple Staff
- [ ] `getPatientsWithMultipleStaff_shouldIncludeCorrectPatients`
- [ ] `getPatientsWithMultipleStaff_shouldIgnoreDuplicates`
- [ ] `getPatientsWithMultipleStaff_shouldReturnEmpty_ifAllSingleStaff`

---

# Integration

- [ ] End-to-end test calling `/api/Patients/never-admitted` with real data
- [ ] Error handling test for bad API data (e.g. malformed JSON)
- [ ] Timeout or failure from upstream API (simulate with MockRestServiceServer)

---

# ApiHelper

## `getAllAdmissions()`
- [ ] Should return a non-null list when API returns valid data
- [ ] Should return empty array when API returns empty list
- [ ] Should throw or handle exception when API returns 500
- [ ] Should handle a 404 error gracefully

## `getAdmissionById(int id)`
- [ ] Should return a valid `Admission` when ID exists
- [ ] Should handle not-found ID (404)
- [ ] Should handle invalid response format
- [ ] Should throw or handle timeout or connection error

## `getAllAllocations()`
- [ ] Should return expected number of `Allocation` objects
- [ ] Should return empty if no allocations exist
- [ ] Should throw or handle error if response is malformed

## `getAllocationById(int id)`
- [ ] Should return correct `Allocation` for given ID
- [ ] Should return null or error for nonexistent ID

## `getAllEmployees()`
- [ ] Should return full list of employees
- [ ] Should handle trailing slash correctly in the URL
- [ ] Should throw or handle if API is unreachable

## `getEmployeeById(int id)`
- [ ] Should fetch employee by valid ID
- [ ] Should return null or handle 404 properly

## `getAllPatients()`
- [ ] Should return a valid array of patients
- [ ] Should handle empty list from API
- [ ] Should throw or handle API errors

## `getPatientById(int id)`
- [ ] Should return patient with given ID
- [ ] Should handle not found ID (404)
- [ ] Should handle unexpected API failure
