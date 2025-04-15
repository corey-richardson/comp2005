# Controller Layer

## F1 – Never Admitted
- [x] `getNeverAdmitted_shouldReturnExpectedPatients`
- [ ] `getNeverAdmitted_shouldReturnEmptyList_whenNoPatientsExist`
- [ ] `getNeverAdmitted_shouldHandleServiceExceptionGracefully`

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

# Integration

- [ ] End-to-end test calling `/api/Patients/never-admitted` with real data
- [ ] Error handling test for bad API data (e.g. malformed JSON)
- [ ] Timeout or failure from upstream API (simulate with MockRestServiceServer)