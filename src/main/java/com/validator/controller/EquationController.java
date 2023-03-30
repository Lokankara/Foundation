package com.validator.controller;

import com.validator.entity.Equation;
import com.validator.entity.Root;
import com.validator.exception.ServiceException;
import com.validator.service.EquationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.validator.controller.Constants.*;
import static com.validator.dao.QueriesContext.*;
import static com.validator.service.Validator.*;
import static java.lang.Long.parseLong;

@Slf4j
@Controller
@AllArgsConstructor
public class EquationController {
    private final EquationService equationService;

    @GetMapping("/equations")
    public String showAll(final Model model) {
        try {
            List<Equation> equations = equationService.find(FIND_ALL);
            model.addAttribute(EQUATION, equations);
            log.info(equations.toString());
        } catch (ServiceException e) {
            return ALL;
        }
        return INDEX;
    }

    @GetMapping("/equations/")
    public final String showEquations(
            @ModelAttribute final Equation equation,
            final Model model) {
        try {
            List<Equation> equations = equationService.find(FIND_ALL);
            model.addAttribute(EQUATION, equations);
            log.info(String.format(found, equations.size()));
            return ALL;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return INDEX;
    }

    @GetMapping("/equations/{id}")
    public String showById(@PathVariable long id, final Model model) {
        try {
            Equation equation = equationService.findById(id);
            log.info(equation.toString());
            model.addAttribute(ROOT, equation);
        } catch (Exception e) {
            model.addAttribute(error, errorMessage);
            log.info(errorMessage);
            return INDEX;
        }
        return ADD;
    }

    @PostMapping("/equations/solve")
    public final String solveEquation(
            @ModelAttribute(ROOT) final String expression,
            @RequestParam("root") final String root,
            @RequestParam("id") final Long id,
            final RedirectAttributes model) {
        try {
            if (expression == null || root == null) {
                return ADD;
            }
            if (checkRootOf(expression, root)) {
                Equation equation = equationService.
                        findByExpression(FIND_BY_EQUATION, expression);
                Root rootBuilder = Root.builder()
                        .root(root).rootId(System.nanoTime())
                        .equation(equation).build();
                log.info(rootBuilder.toString());
                if (equation.addRoot(rootBuilder)) {
                    Equation update = equationService.update(equation);
                    log.info(String.format(updated, update.toString()));
                    return REDIRECT;
                } else {
                    log.info(duplicate);
                    model.addFlashAttribute(error, duplicate);
                    return REDIRECT + id;
                }
            } else {
                String message = String.format("%s is not root of %s", root, expression);
                log.info(String.format("%s solve Equation",message));
                model.addFlashAttribute(error, message);
                return REDIRECT + id;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return INDEX;
    }

    @PostMapping("/equations/add")
    public final String addEquation(
            @RequestParam final String expression,
            final RedirectAttributes model) {

        if (checkExpression(expression)) {
            try {
                String equation = removeSpaces(expression);
                try {
                    if (equationService.findByExpression(FIND_BY_EQUATION, equation) != null) {
                        model.addFlashAttribute(error, duplicateMessage);
                        return INDEX;
                    }
                } catch (ServiceException e) {
                    Equation equationBuild = Equation
                            .builder()
                            .id(System.nanoTime())
                            .expression(equation)
                            .build();
                    solve(equationBuild);
                    log.info(String.format(saved, equationService.save(equationBuild)));
                    return REDIRECT;
                }
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        } else {
            model.addFlashAttribute(error, errorMessage);
            log.info(errorMessage);
        }
        return INDEX;
    }

    @GetMapping("/equations/find")
    public final String findEquations(@ModelAttribute final Equation equation,
            final Model model) {
        String number = "1";
        try {
            List<Equation> singleRootEquations = equationService.find(FIND_BY_SINGLE_ROOT);
            model.addAttribute(EQUATION, singleRootEquations);
            log.info(String.format(found, singleRootEquations.size()));
            return SEARCH;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return INDEX;
    }

    @PostMapping("/equations/find")
    public final String getExpressions(
            @ModelAttribute("equation") final String root,
            @RequestParam("id") final String number,
            final Model model) {
        log.info(root);
        try {
            //TODO
            Long count = parseLong(number);
            List<Equation> findByNumberRoots = equationService.findByCounter(FIND_BY_NUMBER_ROOTS, count);
            List<Equation> equationsByRoot = equationService.find(FIND_BY_ROOT, "root", root);
            List<Equation> singleRootEquations = equationService.find(FIND_BY_SINGLE_ROOT);
            model.addAttribute(EQUATION, findByNumberRoots);
            return SEARCH;
        } catch (ServiceException e) {
            return INDEX;
        }
    }
}
