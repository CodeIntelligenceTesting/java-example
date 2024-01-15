<a href="https://www.code-intelligence.com/">
<img src="https://www.code-intelligence.com/hubfs/Logos/CI%20Logos/Logo_quer_white.png" alt="Code Intelligence logo" width="450px">
</a>

# Java Example

This Java Example is an example project to showcase the usage of white-box fuzz testing for developers.
It features examples for the usage as security issue detector as well as robustness issue detector.

The project contains multiple examples:
* [SpringBoot Examples](src/test/java/com/demo/api/):
Multiple examples ranging from simple to more complex showcasing the testing of APIs.
* [Library Testing Examples](src/test/java/com/demo/libraries):
One example showing how to use fuzz testing to cover the holes in unit testing setups.
* [Property Based Testing Example](src/test/java/com/demo/property_based/PropertyBasedFuzzTest.java):
One example showcasing how Code Intelligence found the [CVE-2021-23899](https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2021-23899)
in the OWASP json-sanitizer library and how fuzz testing can be used for property based testing.