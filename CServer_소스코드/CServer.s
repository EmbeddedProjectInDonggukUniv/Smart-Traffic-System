	.arch armv7-a
	.fpu softvfp
	.eabi_attribute 20, 1
	.eabi_attribute 21, 1
	.eabi_attribute 23, 3
	.eabi_attribute 24, 1
	.eabi_attribute 25, 1
	.eabi_attribute 26, 2
	.eabi_attribute 30, 2
	.eabi_attribute 34, 1
	.eabi_attribute 18, 4
	.file	"CServer.c"
	.section	.text.startup,"ax",%progbits
	.align	2
	.global	main
	.type	main, %function
main:
	@ args = 0, pretend = 0, frame = 24
	@ frame_needed = 0, uses_anonymous_args = 0
	stmfd	sp!, {r4, r5, r6, r7, r8, r9, r10, lr}
	sub	sp, sp, #24
	bl	printIP(PLT)
	movw	r0, #7777
	mov	r1, #1024
	mov	r2, #5
	bl	initServer(PLT)
	ldr	r9, .L15
.LPIC4:
	add	r9, pc, r9
	cmn	r0, #1
	mov	r4, r0
	beq	.L10
	cmn	r0, #2
	beq	.L11
	cmn	r0, #3
	beq	.L12
.L3:
	ldr	r0, .L15+4
	add	r6, sp, #8
	add	r5, sp, #4
	mov	r8, #16
.LPIC3:
	add	r0, pc, r0
	bl	puts(PLT)
	b	.L7
.L14:
	str	r8, [sp, #4]
	bl	accept(PLT)
	cmp	r0, #0
	bgt	.L13
.L7:
	bl	kbhit(PLT)
	mov	r1, r6
	mov	r2, r5
	subs	r7, r0, #0
	mov	r0, r4
	beq	.L14
	ldr	r0, .L15+8
.LPIC5:
	add	r0, pc, r0
	bl	puts(PLT)
	ldr	r3, .L15+12
	mov	r0, r4
	mov	r2, #0
	ldr	r3, [r9, r3]
	str	r2, [r3]
	bl	close(PLT)
	add	sp, sp, #24
	@ sp needed
	ldmfd	sp!, {r4, r5, r6, r7, r8, r9, r10, pc}
.L13:
	ldr	r2, .L15+16
	mov	r3, r0
	mov	r1, r7
	mov	r0, sp
	ldr	r2, [r9, r2]
	bl	pthread_create(PLT)
	b	.L7
.L10:
	ldr	r0, .L15+20
.LPIC0:
	add	r0, pc, r0
	bl	err_quit(PLT)
	b	.L3
.L11:
	ldr	r0, .L15+24
.LPIC1:
	add	r0, pc, r0
	bl	err_quit(PLT)
	b	.L3
.L12:
	ldr	r0, .L15+28
.LPIC2:
	add	r0, pc, r0
	bl	err_quit(PLT)
	b	.L3
.L16:
	.align	2
.L15:
	.word	_GLOBAL_OFFSET_TABLE_-(.LPIC4+8)
	.word	.LC5-(.LPIC3+8)
	.word	.LC6-(.LPIC5+8)
	.word	program_run(GOT)
	.word	recvThread(GOT)
	.word	.LC2-(.LPIC0+8)
	.word	.LC3-(.LPIC1+8)
	.word	.LC4-(.LPIC2+8)
	.size	main, .-main
	.text
	.align	2
	.global	printPacketInfo
	.type	printPacketInfo, %function
printPacketInfo:
	@ args = 0, pretend = 0, frame = 0
	@ frame_needed = 0, uses_anonymous_args = 0
	stmfd	sp!, {r3, r4, r5, r6, r7, r8, r9, r10, fp, lr}
	mov	ip, #0
	ldr	r4, .L34
	mov	r10, r1
	ldrb	r5, [r0, #3]	@ zero_extendqisi2
	mov	r3, #2
	ldrb	r6, [r0, #4]	@ zero_extendqisi2
.LPIC15:
	add	r4, pc, r4
	ldrb	r7, [r0, #6]	@ zero_extendqisi2
	ldrb	r8, [r0, #7]	@ zero_extendqisi2
	ldrb	fp, [r0, #8]	@ zero_extendqisi2
	ldrb	r9, [r0, #9]	@ zero_extendqisi2
	ldrb	r2, [r0, #14]	@ zero_extendqisi2
.L19:
	ldrb	r1, [r0, r3]	@ zero_extendqisi2
	add	r3, r3, #1
	cmp	r3, #14
	add	ip, ip, r1
	uxtb	ip, ip
	bne	.L19
	cmp	r2, ip
	beq	.L30
	ldr	r0, .L34+4
	mov	r1, r5
	ldmfd	sp!, {r3, r4, r5, r6, r7, r8, r9, r10, fp, lr}
.LPIC27:
	add	r0, pc, r0
	b	printf(PLT)
.L30:
	ldr	r3, .L34+8
	ldr	r0, .L34+12
	ldr	r3, [r4, r3]
.LPIC14:
	add	r0, pc, r0
	ldr	r1, [r3]
	bl	printf(PLT)
	cmp	r6, #2
	beq	.L31
	cmp	r6, #1
	ldmnefd	sp!, {r3, r4, r5, r6, r7, r8, r9, r10, fp, pc}
	ldr	r0, .L34+16
	mov	r1, r5
	mov	r2, r7
.LPIC21:
	add	r0, pc, r0
	bl	printf(PLT)
	cmp	r7, #1
	ldmnefd	sp!, {r3, r4, r5, r6, r7, r8, r9, r10, fp, pc}
	ldr	r3, [r10]
	cmp	r3, #0
	ldmnefd	sp!, {r3, r4, r5, r6, r7, r8, r9, r10, fp, pc}
	ldr	r0, .L34+20
	ldr	r1, .L34+24
.LPIC22:
	add	r0, pc, r0
.LPIC23:
	add	r1, pc, r1
	bl	fopen(PLT)
	ldr	r3, .L34+28
	ldr	r3, [r4, r3]
	ldr	r2, [r3]
	add	r2, r2, #1
	str	r2, [r3]
	str	r7, [r10]
	subs	r4, r0, #0
	beq	.L32
	ldr	r0, .L34+32
	mov	r1, r7
	mov	r3, r4
	mov	r2, #8
.LPIC25:
	add	r0, pc, r0
	bl	fwrite(PLT)
	ldr	r0, .L34+36
.LPIC26:
	add	r0, pc, r0
	bl	puts(PLT)
	mov	r0, r4
	ldmfd	sp!, {r3, r4, r5, r6, r7, r8, r9, r10, fp, lr}
	b	fclose(PLT)
.L31:
	orr	r9, r9, fp, asl #8
	movw	r3, #26215
	movt	r3, 26214
	orr	r7, r8, r7, asl #8
	mov	r1, r5
	ldr	r0, .L34+40
	smull	r2, r3, r3, r9
	add	r7, r7, r7, asl #1
.LPIC16:
	add	r0, pc, r0
	mov	r6, r7, asl #1
	mov	r2, r6
	mov	r5, r3, lsr #2
	mov	r3, r5
	bl	printf(PLT)
	ldr	r3, .L34+44
	ldr	r0, .L34+48
	cmp	r5, #30
	ldr	r1, .L34+52
	ldr	r3, [r4, r3]
.LPIC17:
	add	r0, pc, r0
.LPIC18:
	add	r1, pc, r1
	movgt	r2, #1
	movle	r2, #0
	str	r2, [r3]
	bl	fopen(PLT)
	subs	r4, r0, #0
	beq	.L33
	ldr	r1, .L34+56
	mov	r2, r6
.LPIC20:
	add	r1, pc, r1
	bl	fprintf(PLT)
	mov	r0, r4
	ldmfd	sp!, {r3, r4, r5, r6, r7, r8, r9, r10, fp, lr}
	b	fclose(PLT)
.L33:
	ldr	r0, .L34+60
	ldmfd	sp!, {r3, r4, r5, r6, r7, r8, r9, r10, fp, lr}
.LPIC19:
	add	r0, pc, r0
	b	printf(PLT)
.L32:
	ldr	r0, .L34+64
	ldmfd	sp!, {r3, r4, r5, r6, r7, r8, r9, r10, fp, lr}
.LPIC24:
	add	r0, pc, r0
	b	printf(PLT)
.L35:
	.align	2
.L34:
	.word	_GLOBAL_OFFSET_TABLE_-(.LPIC15+8)
	.word	.LC18-(.LPIC27+8)
	.word	client_cnt(GOT)
	.word	.LC7-(.LPIC14+8)
	.word	.LC13-(.LPIC21+8)
	.word	.LC14-(.LPIC22+8)
	.word	.LC10-(.LPIC23+8)
	.word	total_car_crushed(GOT)
	.word	.LC16-(.LPIC25+8)
	.word	.LC17-(.LPIC26+8)
	.word	.LC8-(.LPIC16+8)
	.word	brightness(GOT)
	.word	.LC9-(.LPIC17+8)
	.word	.LC10-(.LPIC18+8)
	.word	.LC12-(.LPIC20+8)
	.word	.LC11-(.LPIC19+8)
	.word	.LC15-(.LPIC24+8)
	.size	printPacketInfo, .-printPacketInfo
	.align	2
	.global	sendPacket
	.type	sendPacket, %function
sendPacket:
	@ args = 0, pretend = 0, frame = 32
	@ frame_needed = 0, uses_anonymous_args = 0
	stmfd	sp!, {r4, r5, r6, r7, r8, r9, r10, fp, lr}
	sub	sp, sp, #36
	ldr	r10, .L41
	mov	r8, r1
	ldr	r7, .L41+4
	add	r9, sp, #12
.LPIC28:
	add	r10, pc, r10
	str	r0, [sp, #4]
	sub	r4, r1, #1
.LPIC29:
	add	r7, pc, r7
	ldr	r2, [r10, #8]	@ unaligned
	mov	r6, r9
	ldr	r3, [r10, #12]	@ unaligned
	add	ip, sp, #15
	ldr	r0, [r10]	@ unaligned
	add	r5, sp, #25
	ldr	r1, [r10, #4]	@ unaligned
	mov	lr, #0
	ldrb	fp, [r10, #16]	@ zero_extendqisi2
	ldr	r10, .L41+8
	stmia	r6!, {r0, r1, r2, r3}
	strb	fp, [r6]
	ldr	r2, [r7, r10]
	ldr	r3, .L41+12
	ldr	r2, [r2]
	strb	r2, [r8, #6]
	ldr	r3, [r7, r3]
	ldr	r3, [r3]
	strb	r3, [r8, #8]
.L38:
	ldrb	r3, [r4, #1]!	@ zero_extendqisi2
	add	lr, lr, r3
	strb	r3, [ip, #1]!
	cmp	ip, r5
	uxtb	lr, lr
	bne	.L38
	ldr	r3, .L41+16
	mov	r1, r9
	ldr	r0, [sp, #4]
	ldr	r3, [r7, r3]
	strb	lr, [sp, #26]
	ldr	r2, [r3]
	cmp	r2, #16
	moveq	r2, #0
	streq	r2, [r3]
	mov	r2, #17
	mov	r3, #0
	bl	send(PLT)
	add	sp, sp, #36
	@ sp needed
	ldmfd	sp!, {r4, r5, r6, r7, r8, r9, r10, fp, pc}
.L42:
	.align	2
.L41:
	.word	.LANCHOR0-(.LPIC28+8)
	.word	_GLOBAL_OFFSET_TABLE_-(.LPIC29+8)
	.word	total_car_crushed(GOT)
	.word	brightness(GOT)
	.word	cnt(GOT)
	.size	sendPacket, .-sendPacket
	.align	2
	.global	recvThread
	.type	recvThread, %function
recvThread:
	@ args = 0, pretend = 0, frame = 1088
	@ frame_needed = 0, uses_anonymous_args = 0
	stmfd	sp!, {r4, r5, r6, r7, r8, r9, r10, fp, lr}
	sub	sp, sp, #1088
	ldr	r5, .L67
	sub	sp, sp, #4
	ldr	r3, .L67+4
	mov	r6, #0
.LPIC30:
	add	r5, pc, r5
	str	r0, [sp, #8]
	str	r6, [sp, #28]
	ldr	r3, [r5, r3]
	ldr	r0, .L67+8
	ldr	r1, [r3]
.LPIC31:
	add	r0, pc, r0
	str	r3, [sp, #16]
	add	r1, r1, #1
	str	r1, [r3]
	bl	printf(PLT)
	mov	r0, #1
	bl	sleep(PLT)
	ldr	r1, .L67+12
	ldr	r0, [sp, #8]
	mov	r3, r6
	mov	r2, #17
	ldr	r1, [r5, r1]
	bl	send(PLT)
	cmp	r0, r6
	blt	.L44
	ldr	r8, .L67+16
	add	r7, sp, #64
	ldr	r9, .L67+20
	mov	r4, r6
	ldr	r3, .L67+24
.LPIC37:
	add	r8, pc, r8
	ldr	r1, .L67+28
.LPIC36:
	add	r9, pc, r9
	add	r2, sp, #44
	str	r2, [sp, #12]
.LPIC35:
	add	r1, pc, r1
	ldr	fp, [r5, r3]
	str	r1, [sp, #20]
.L48:
	ldr	r3, [fp]
	cmp	r3, #0
	beq	.L57
	ldr	r0, [sp, #8]
	mov	r1, r7
	movw	r2, #1023
	mov	r3, #0
	bl	recv(PLT)
	subs	r10, r0, #0
	blt	.L48
	beq	.L57
	mov	r3, r4
	mov	r5, #0
	b	.L55
.L66:
	ldrb	r3, [r3, #-1045]	@ zero_extendqisi2
	cmp	r3, #10
	beq	.L64
.L51:
	cmp	r4, #19
	bgt	.L65
.L52:
	add	r5, r5, #1
	cmp	r5, r10
	bge	.L48
	mov	r3, r4
.L55:
	ldrb	r2, [r7, r5]	@ zero_extendqisi2
	add	r1, sp, #1088
	add	r3, r1, r3
	cmp	r6, #0
	add	r4, r4, #1
	strb	r2, [r3, #-1044]
	bne	.L66
	cmp	r4, #1
	beq	.L52
	ldrb	r3, [sp, #44]	@ zero_extendqisi2
	cmp	r3, #160
	bne	.L53
	ldrb	r3, [sp, #45]	@ zero_extendqisi2
	cmp	r3, #10
	moveq	r6, #1
	beq	.L52
.L53:
	mov	r0, r8
	mov	r4, #0
	bl	puts(PLT)
	b	.L52
.L65:
	mov	r0, r9
	mov	r6, #0
	bl	puts(PLT)
	mov	r4, r6
	b	.L52
.L64:
	cmp	r2, #160
	bne	.L51
	cmp	r4, #17
	bne	.L51
	ldr	r0, [sp, #12]
	add	r1, sp, #28
	add	r3, sp, #32
	str	r3, [sp, #4]
	bl	printPacketInfo(PLT)
	ldrb	r2, [sp, #48]	@ zero_extendqisi2
	ldr	r3, [sp, #4]
	cmp	r2, #2
	moveq	r6, #0
	moveq	r4, r6
	beq	.L52
	ldr	ip, [sp, #20]
	mov	r2, r3
	mov	r6, #0
	mov	r4, r6
	ldr	r0, [ip, #20]!	@ unaligned
	ldr	r1, [ip, #4]	@ unaligned
	ldrh	ip, [ip, #8]	@ unaligned
	stmia	r2!, {r0, r1}
	mov	r1, r3
	ldr	r0, [sp, #8]
	strh	ip, [r2]	@ unaligned
	bl	sendPacket(PLT)
	b	.L52
.L57:
	ldr	r1, [sp, #16]
	ldr	r0, [sp, #8]
	ldr	r3, [r1]
	sub	r3, r3, #1
	str	r3, [r1]
	bl	close(PLT)
	ldr	r0, .L67+32
.LPIC34:
	add	r0, pc, r0
	bl	puts(PLT)
	mov	r0, #0
	add	sp, sp, #1088
	add	sp, sp, #4
	@ sp needed
	ldmfd	sp!, {r4, r5, r6, r7, r8, r9, r10, fp, pc}
.L44:
	ldr	r0, .L67+36
.LPIC32:
	add	r0, pc, r0
	bl	puts(PLT)
	ldr	r0, .L67+40
.LPIC33:
	add	r0, pc, r0
	bl	puts(PLT)
	ldr	r0, [sp, #8]
	bl	close(PLT)
	ldr	r2, [sp, #16]
	mov	r0, #0
	ldr	r3, [r2]
	sub	r3, r3, #1
	str	r3, [r2]
	add	sp, sp, #1088
	add	sp, sp, #4
	@ sp needed
	ldmfd	sp!, {r4, r5, r6, r7, r8, r9, r10, fp, pc}
.L68:
	.align	2
.L67:
	.word	_GLOBAL_OFFSET_TABLE_-(.LPIC30+8)
	.word	client_cnt(GOT)
	.word	.LC19-(.LPIC31+8)
	.word	SENSOR_REQ_PACKET(GOT)
	.word	.LC23-(.LPIC37+8)
	.word	.LC22-(.LPIC36+8)
	.word	program_run(GOT)
	.word	.LANCHOR0-(.LPIC35+8)
	.word	.LC21-(.LPIC34+8)
	.word	.LC20-(.LPIC32+8)
	.word	.LC21-(.LPIC33+8)
	.size	recvThread, .-recvThread
	.global	cnt
	.global	brightness
	.global	total_car_crushed
	.global	client_cnt
	.global	program_run
	.global	SENSOR_REQ_PACKET
	.section	.rodata
	.align	2
.LANCHOR0 = . + 0
.LC0:
	.byte	-96
	.byte	10
	.byte	0
	.byte	0
	.byte	0
	.byte	0
	.byte	0
	.byte	0
	.byte	0
	.byte	0
	.byte	0
	.byte	0
	.byte	0
	.byte	0
	.byte	0
	.byte	10
	.byte	-96
	.space	3
.LC1:
	.byte	0
	.byte	1
	.byte	2
	.byte	3
	.byte	4
	.byte	5
	.byte	6
	.byte	7
	.byte	8
	.byte	9
	.data
	.align	2
	.type	program_run, %object
	.size	program_run, 4
program_run:
	.word	1
	.type	SENSOR_REQ_PACKET, %object
	.size	SENSOR_REQ_PACKET, 17
SENSOR_REQ_PACKET:
	.byte	-96
	.byte	10
	.byte	0
	.byte	0
	.byte	1
	.byte	0
	.byte	0
	.byte	0
	.byte	0
	.byte	0
	.byte	0
	.byte	0
	.byte	0
	.byte	0
	.byte	1
	.byte	10
	.byte	-96
	.section	.rodata.str1.4,"aMS",%progbits,1
	.align	2
.LC2:
	.ascii	"socket() error\000"
	.space	1
.LC3:
	.ascii	"bind() error\000"
	.space	3
.LC4:
	.ascii	"listen() error\000"
	.space	1
.LC5:
	.ascii	"Server Started\000"
	.space	1
.LC6:
	.ascii	"Server Closed\000"
	.space	2
.LC7:
	.ascii	"total : %d \000"
.LC8:
	.ascii	"SpeedGun [%03d], speed: %d, light: %d\012\000"
	.space	1
.LC9:
	.ascii	"/mnt/sdcard/dir/speed.txt\000"
	.space	2
.LC10:
	.ascii	"wt\000"
	.space	1
.LC11:
	.ascii	"fail in speedgun\000"
	.space	3
.LC12:
	.ascii	"%d\000"
	.space	1
.LC13:
	.ascii	"Car [%03d], crashed: %d \012\000"
	.space	2
.LC14:
	.ascii	"/mnt/sdcard/dir/accident.txt\000"
	.space	3
.LC15:
	.ascii	"Crashed\000"
.LC16:
	.ascii	"accident\000"
	.space	3
.LC17:
	.ascii	"ok accident\000"
.LC18:
	.ascii	"[%03d] Checksum error\012\000"
	.space	1
.LC19:
	.ascii	"Client connected (Total:%d)\012\000"
	.space	3
.LC20:
	.ascii	"send() ERROR\000"
	.space	3
.LC21:
	.ascii	"Client disconnected\000"
.LC22:
	.ascii	"BUFFER OVERFLOW ERROR\000"
	.space	2
.LC23:
	.ascii	"START PACKET ERROR\000"
	.bss
	.align	2
	.type	cnt, %object
	.size	cnt, 4
cnt:
	.space	4
	.type	brightness, %object
	.size	brightness, 4
brightness:
	.space	4
	.type	total_car_crushed, %object
	.size	total_car_crushed, 4
total_car_crushed:
	.space	4
	.type	client_cnt, %object
	.size	client_cnt, 4
client_cnt:
	.space	4
	.ident	"GCC: (GNU) 4.8"
	.section	.note.GNU-stack,"",%progbits
