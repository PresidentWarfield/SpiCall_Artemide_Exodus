# SpiCall | Artemide | Spartacus : Possible initiator of Exodus Android and iOS Spyware Campaign
Reversing of the C2C SpiCall | Artemide | Spartacus | Possible initiator of Exodus Android and iOS Spyware Campaign

## Preamble
This a project created for **Malware Analysis** and **Research purpose**, and is the last step of a [standard procedure](https://github.com/users/PresidentWarfield/projects/1) aimed **to research threat techniques and develop defenses**.

## This Mass Surveillance Spyware campaing is named "**SpiCall | Artemide | Spartacus**" from the details present into the extracted electronic certificate (please refer to images at page bottom).

**This repository was (somehow) linked to the Exodus mobile malware campaing because**:
- The package name of the malicious app (that was supposed to be no longer) included in the on-line store was "**it.nv.wat**"
  -This identifier was immediately associated to a number of apps flagged as malicious, and promptly removed from the store since the end of March 2019 (**possibly also by the developer itself**) because probably associated to Exodus spyware family
- Match / Correlation performed by hash and developer API value(s) confirmed the malicious nature of the App
  - Developer token (restricted due to privacy) was associated to illegitimate / malicious behaviours as well
  - MD5 checksum of the analysed App was already fingerprinting the spyware via the following hash: **edeea0b34d3f1298a6930e3970401d82**

## Unexpectedly
 At the moment of the **update** of this README file (**11 APR 2019**), another **spy-app** with the same package name ("**it.nv.wat**"), same fingerprints but (of course) different MD5 checkusum (**e0d5e0b626183e13c97d2719383c5dd7**) it's again available at the following address (hopefully it will be removed soon): **hXXps://play.google.com/store/apps/details?id=it.nv.wat**

![](/screenshots/screenshot005.png)

![](/screenshots/screenshot004.png)

## Legitimate question
**Why this (spy)App and similar ones are still / again available as a public resource?**

**Did the developer(s) publish it again after the so called "Exodus" wave?**

![](/screenshots/screencast.gif)

## Stated that, we think that those repositories are meaningful because everyone can understand how a privacy leak works:
### Discover it yourself
- See how (e.g.) [WhatsApp is intercepted on a phone](https://github.com/PresidentWarfield/SpiCall_Artemide_Exodus/search?p=2&q=whatsapp&unscoped_q=whatsapp):
```
https://github.com/PresidentWarfield/SpiCall_Artemide_Exodus/search?p=2&q=whatsapp&unscoped_q=whatsapp
```

- See how (e.g.) [C2C commands are executed on a phone](https://github.com/PresidentWarfield/SpiCall_Artemide_Exodus/search?q=CMD_NAME&unscoped_q=CMD_NAME):
```
https://github.com/PresidentWarfield/SpiCall_Artemide_Exodus/search?q=CMD_NAME&unscoped_q=CMD_NAME
```

## Truncated for Privacy
Some piece of the code are truncated to avoid enticing unauthorized or unethical actions, but in any case the understanding of the code remains unchanged.

## Public Domain
The contents included into this repository have been released into the public domain since the original working application has been deliberately made public without restrictions (as a method of mass infection) through app stores.

## General Consideration
The code is a potpourri of already known instruction sets*, but **has exposed both the producer and the victim to very high privacy risks**.

(*) The App reversed into this repo looks **very similar** to another one we analyzed (wondering **why a stock viewer App needs to record your voice**):
hXXps://play.google.com/store/apps/details?id=com.accadia.android
but with part of the instruction set well localized in Italian language.

![](/screenshots/screenshot006.png)

## Peculiarity
The MQTT protocol to manage the C2C operations

## For Governments
It would be enough to backtrack or simply use:
- App-Store / Developer token
  - To know the real email address / identity of the developer(s)
- The built in RSA key
  - That gives consistent "tips & tricks" about remote authentication mechanisms on resources where all the data collected are stored indiscriminately
- The Fabric / CrashLytics credentials
  - To see which devices are / were infected
  
At present many C2C servers are still up togheter with several stolen data repositories (some of those shows TCP activity on port TCP 60129 - Darktrack RAT / Dropbear SSHd)

## Conclusion
Cyber criminals play tricks creating confusion over expired and promptly renewed domain names, app names, etc..

**Exodus was just a clumsy and miserably failed attempt to copy** the "Multilevel Business Model of Mass Surveillance Market" explained here:

![](/screenshots/D3yRhKWW0AEkPGX.jpg)

Where they failed at point 3 of the workflow, while playing the role of the actor that within intelligence investigations is called "**The Mule**".

That's it.

---
![](/screenshots/screenshot001.png)

![](/screenshots/screenshot003.png)

![](/screenshots/screenshot002.png)

---
https://presidentwarfield.github.io/SpiCall_Artemide_Exodus/
