fastlane documentation
================
# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```
xcode-select --install
```

## Choose your installation method:

<table width="100%" >
<tr>
<th width="33%"><a href="http://brew.sh">Homebrew</a></td>
<th width="33%">Installer Script</td>
<th width="33%">Rubygems</td>
</tr>
<tr>
<td width="33%" align="center">macOS</td>
<td width="33%" align="center">macOS</td>
<td width="33%" align="center">macOS or Linux with Ruby 2.0.0 or above</td>
</tr>
<tr>
<td width="33%"><code>brew cask install fastlane</code></td>
<td width="33%"><a href="https://download.fastlane.tools">Download the zip file</a>. Then double click on the <code>install</code> script (or run it in a terminal window).</td>
<td width="33%"><code>sudo gem install fastlane -NV</code></td>
</tr>
</table>

# Available Actions
## Android
### android config
```
fastlane android config
```
Output configuration for debugging
### android dependency_updates
```
fastlane android dependency_updates
```
Show update dependencies
### android compile
```
fastlane android compile
```
Compile debug and test sources
### android lint
```
fastlane android lint
```
Execute Android lint
### android assemble
```
fastlane android assemble
```
Assemble source and test APKs
### android unit_test
```
fastlane android unit_test
```
Execute unit test
### android instrumentation_test
```
fastlane android instrumentation_test
```
Execute instrumentation test on Emulator
### android pipeline
```
fastlane android pipeline
```
Local pipeline execution

----

This README.md is auto-generated and will be re-generated every time [fastlane](https://fastlane.tools) is run.
More information about fastlane can be found on [fastlane.tools](https://fastlane.tools).
The documentation of fastlane can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
