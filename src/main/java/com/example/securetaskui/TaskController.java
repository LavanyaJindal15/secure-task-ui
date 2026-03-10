package com.example.securetaskui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository repo;

    public TaskController(TaskRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", repo.findAll());
        model.addAttribute("taskForm", new TaskEntity());
        return "tasks";
    }

    @PostMapping
    public String createTask(@ModelAttribute("taskForm") TaskEntity task) {
        repo.save(task);
        return "redirect:/tasks";
    }

    // TODO C
    @GetMapping("/{id}/edit")
    public String editTask(@PathVariable("id") Long id, Model model) {
        TaskEntity task = repo.findById(id);
        model.addAttribute("taskForm", task);
        model.addAttribute("tasks", repo.findAll());
        return "tasks";
    }

    // TODO D
    @PostMapping("/{id}/update")
    public String updateTask(@PathVariable("id") Long id,
                             @ModelAttribute("taskForm") TaskEntity formTask) {
        TaskEntity existingTask = repo.findById(id);
        if (existingTask != null) {
            existingTask.setTitle(formTask.getTitle());
            existingTask.setDescription(formTask.getDescription());
            existingTask.setCompleted(formTask.isCompleted());
            repo.save(existingTask);
        }
        return "redirect:/tasks";
    }

    // TODO E
    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable("id") Long id) {
        repo.deleteById(id);
        return "redirect:/tasks";
    }
}